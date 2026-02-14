package de.derfrzocker.anime.calendar.server.impl.season.anidb.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "integration.anidb.udp-client")
public interface AniDBUDPClientConfig {

    @WithName("username")
    String username();

    @WithName("password")
    String password();

    @WithName("client-id")
    String clientId();

    @WithName("client-version")
    String clientVersion();

    @WithName("local-port")
    int localPort();

    @WithName("address")
    String address();

    @WithName("port")
    int port();

    @WithName("timeout")
    int timeout();

    @WithName("sleep-time")
    long sleepTime();
}
