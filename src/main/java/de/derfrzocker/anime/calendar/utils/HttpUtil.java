package de.derfrzocker.anime.calendar.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public final class HttpUtil {

    private HttpUtil() {
    }

    public static void sendResponse(HttpExchange exchange, String response) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, 0);
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public static void sendCode(HttpExchange exchange, int code) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(code, 0);
        outputStream.flush();
        outputStream.close();
    }
}
