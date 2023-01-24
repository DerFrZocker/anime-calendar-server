package de.derfrzocker.anime.calendar.server.calendar;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.derfrzocker.anime.calendar.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.api.CalendarService;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.utils.HttpUtil;
import net.fortuna.ical4j.model.Calendar;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class PersonalRequestHandler implements HttpHandler {

    @NotNull
    private final CalendarService calendarService;

    public PersonalRequestHandler(@NotNull CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String[] arguments = exchange.getRequestURI().getPath().replace("/v2/personal/", "").split("/");
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                if (arguments.length != 3) {
                    // 400 bad request
                    HttpUtil.sendCode(exchange, 400);
                    return;
                }

                handleGetCalendarRequest(exchange, arguments[0], arguments[1], arguments[2]);
                return;
            }

            if ("PUT".equalsIgnoreCase(exchange.getRequestMethod())) {
                if (arguments.length != 2) {
                    // 400 bad request
                    HttpUtil.sendCode(exchange, 400);
                    return;
                }

                if (!"anime-calendar".equals(arguments[0])) {
                    // 400 bad request
                    HttpUtil.sendCode(exchange, 400);
                    return;
                }

                handlePutAnimeCalendarIds(exchange, arguments[1]);
                return;
            }

            HttpUtil.sendCode(exchange, 405);
        } catch (Throwable e) {
            HttpUtil.sendCode(exchange, 500);
            e.printStackTrace();
        }

    }

    private void handleGetCalendarRequest(HttpExchange exchange, String idProvider, String userId, String regionName) throws IOException {
        if (idProvider.isBlank() || userId.isBlank() || regionName.isBlank()) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            return;
        }
        if (!isValid(userId)) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            return;
        }
        if (!"anime-calendar".equals(idProvider)) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            return;
        }

        Region region;
        try {
            region = Region.valueOf(regionName.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            e.printStackTrace();
            return;
        }

        List<Integer> animeIds = calendarService.getAnimeIds(idProvider, userId);
        Calendar calendar = calendarService.buildCalendar(animeIds, AnimeOptionsBuilder.anAnimeOptions(region).build());
        exchange.getResponseHeaders().set("Content-Type", "text/calendar");
        HttpUtil.sendResponse(exchange, calendar.toString());
    }

    private void handlePutAnimeCalendarIds(HttpExchange exchange, String userId) throws IOException {
        if (userId.isBlank()) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            return;
        }
        if (!isValid(userId)) {
            // 400 bad request
            HttpUtil.sendCode(exchange, 400);
            return;
        }

        List<Integer> integers = new ArrayList<>();
        try (Reader reader = new InputStreamReader(exchange.getRequestBody())) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (!jsonElement.isJsonArray()) {
                // 400 bad request
                HttpUtil.sendCode(exchange, 400);
                return;
            }


            for (JsonElement entry : jsonElement.getAsJsonArray()) {
                if (!entry.isJsonPrimitive() || !entry.getAsJsonPrimitive().isNumber()) {
                    // 400 bad request
                    HttpUtil.sendCode(exchange, 400);
                    return;
                }

                integers.add(entry.getAsNumber().intValue());
            }
        }

        calendarService.saveAnimeIds("anime-calendar", userId, integers);
        HttpUtil.sendCode(exchange, 200);
    }

    private boolean isValid(String string) {
        if (string.length() > 128) {
            return false;
        }

        boolean valid = true;
        for (char c : string.toCharArray()) {
            valid = ((c >= 'a') && (c <= 'z')) ||
                    ((c >= 'A') && (c <= 'Z')) ||
                    ((c >= '0') && (c <= '9'));

            if (!valid) {
                break;
            }
        }

        return valid;
    }
}