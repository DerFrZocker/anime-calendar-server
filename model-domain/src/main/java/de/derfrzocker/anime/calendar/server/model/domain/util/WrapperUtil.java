package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.function.Function;

public final class WrapperUtil {

    private WrapperUtil() {
    }

    public static <T, R> R unwrapSafe(T value, Function<T, R> function) {
        if (value == null) {
            return null;
        }

        return function.apply(value);
    }

    private static <T> String toString(T id) {
        if (id == null) {
            return "<null>";
        }

        return String.valueOf(id);
    }
}
