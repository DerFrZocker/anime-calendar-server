package de.derfrzocker.anime.calendar.server.rest.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Response {

    public static <T, R> Collector<T, ?, R> ofType(Function<List<T>, R> response) {
        return Collectors.collectingAndThen(Collectors.toList(), response);
    }

    private Response() {

    }
}
