package de.derfrzocker.anime.calendar.server.impl.season.anidb.client;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import jakarta.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
public class AniDBUDPClient {

    private static final String ANI_DB_UDP_VERSION = "3";
    private static final int UDP_PACKET_SIZE = 4096;

    @ConfigProperty(name = "anidb.season-update.udp.username")
    String username;
    @ConfigProperty(name = "anidb.season-update.udp.password")
    String password;
    @ConfigProperty(name = "anidb.season-update.udp.client-id")
    String clientId;
    @ConfigProperty(name = "anidb.season-update.udp.client-version")
    String clientVersion;
    @ConfigProperty(name = "anidb.season-update.udp.local-port")
    int localPort;
    @ConfigProperty(name = "anidb.season-update.udp.address")
    String address;
    @ConfigProperty(name = "anidb.season-update.udp.port")
    int port;
    @ConfigProperty(name = "anidb.season-update.udp.timeout")
    int timeout;
    @ConfigProperty(name = "anidb.season-update.udp.sleep-time")
    long sleepTime;

    public List<AniDBSeasonInfo> getSeasonData() {
        try (DatagramSocket datagramSocket = new DatagramSocket(localPort)) {
            datagramSocket.connect(InetAddress.getByName(address), port);
            datagramSocket.setSoTimeout(timeout);

            String session = auth(datagramSocket);
            String result = readCalendar(datagramSocket, session);
            logout(datagramSocket, session);

            datagramSocket.disconnect();

            return Stream.of(result.split("\n")).filter(data -> !data.equals("297 CALENDAR")).map(data -> {
                String[] split = data.split("\\|");
                if (split.length != 3) {
                    // TODO 2024-12-18: Better exception
                    throw new IllegalArgumentException("Invalid season data: " + data);
                }

                IntegrationAnimeId externalAnimeId = new IntegrationAnimeId(split[0]);
                Instant startTime = Instant.ofEpochSecond(Long.parseLong(split[1]));

                return new AniDBSeasonInfo(externalAnimeId, startTime);
            }).toList();
        } catch (IOException | InterruptedException e) {
            // TODO 2024-12-17: Better exception
            throw new RuntimeException(e);
        }
    }

    private String auth(DatagramSocket datagramSocket) throws IOException, InterruptedException {
        send(datagramSocket,
             "AUTH user=%s&pass=%s&protover=%s&client=%s&clientver=%s".formatted(username,
                                                                                 password,
                                                                                 ANI_DB_UDP_VERSION,
                                                                                 clientId,
                                                                                 clientVersion));
        String result = receive(datagramSocket);

        if (!result.startsWith("200 ") && !result.startsWith("201 ")) {
            // TODO 2024-12-17: Better exception
            throw new RuntimeException("Failed to authenticate user: " + result);
        }

        result = result.substring("200 ".length());
        result = result.substring(0, result.indexOf(' '));

        return result;
    }

    private String readCalendar(DatagramSocket datagramSocket, String session) throws
                                                                               IOException,
                                                                               InterruptedException {
        send(datagramSocket, "CALENDAR s=%s".formatted(session));

        String response = receive(datagramSocket);

        if (!response.contains("|")) {
            // TODO 2024-12-17: Better exception
            throw new RuntimeException("Failed to read calendar: " + response);
        }

        return response;
    }

    private void logout(DatagramSocket datagramSocket, String session) throws IOException, InterruptedException {
        send(datagramSocket, "LOGOUT s=%s".formatted(session));
        receive(datagramSocket);
    }

    private void send(DatagramSocket datagramSocket, String dataString) throws IOException, InterruptedException {
        // TODO 2024-12-17: Check if this can be made better
        Thread.sleep(sleepTime); // The UDP API is rated limited, so we slow down
        byte[] data = dataString.getBytes(StandardCharsets.US_ASCII);
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        datagramSocket.send(datagramPacket);
    }

    private String receive(DatagramSocket datagramSocket) throws IOException, InterruptedException {
        DatagramPacket packet = new DatagramPacket(new byte[UDP_PACKET_SIZE], UDP_PACKET_SIZE);
        datagramSocket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength(), StandardCharsets.US_ASCII);
    }
}
