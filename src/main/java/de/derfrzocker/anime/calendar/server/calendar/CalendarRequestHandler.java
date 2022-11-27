package de.derfrzocker.anime.calendar.server.calendar;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarRequestHandler implements HttpHandler {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final CalendarBuilder calendarBuilder;

    public CalendarRequestHandler(CalendarBuilder calendarBuilder) {
        this.calendarBuilder = calendarBuilder;
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/v1/", this);
        server.setExecutor(executorService);
        server.start();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod()) && exchange.getRequestURI().getPath().equals("/v1/personal")) {
            String calendar = "";
            try {
                calendar = calendarBuilder.getCalendar(exchange.getRequestURI()).toString();
            } catch (ParameterException e) {
                // 400 bad request
                OutputStream outputStream = exchange.getResponseBody();
                exchange.sendResponseHeaders(500, e.getMessage().length());
                outputStream.write(e.getMessage().getBytes());
                outputStream.flush();
                outputStream.close();
                return;
            } catch (Exception e) {
                // 500 internal server
                OutputStream outputStream = exchange.getResponseBody();
                exchange.sendResponseHeaders(500, 0);
                outputStream.flush();
                outputStream.close();
                e.printStackTrace();
                return;
            }
            exchange.getResponseHeaders().set("Content-Type", "text/calendar");
            OutputStream outputStream = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, 0);
            outputStream.write(calendar.getBytes());
            outputStream.flush();
            outputStream.close();
            return;
        }
        // 405 method not allowed
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(405, 0);
        outputStream.flush();
        outputStream.close();
    }
}
