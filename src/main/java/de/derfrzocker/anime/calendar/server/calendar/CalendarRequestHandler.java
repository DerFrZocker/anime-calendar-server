package de.derfrzocker.anime.calendar.server.calendar;

import com.sun.net.httpserver.HttpServer;
import de.derfrzocker.anime.calendar.api.CalendarService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarRequestHandler {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final CalendarService calendarService;

    public CalendarRequestHandler(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/v2/personal/", new PersonalRequestHandler(calendarService));
        server.setExecutor(executorService);
        server.start();
    }
}
