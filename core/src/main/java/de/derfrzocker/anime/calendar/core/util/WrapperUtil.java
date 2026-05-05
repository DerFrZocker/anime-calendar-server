package de.derfrzocker.anime.calendar.core.util;

import java.util.function.Function;
import org.jetbrains.annotations.Contract;

public final class WrapperUtil {

    private WrapperUtil() {
    }

    @Contract("null,_ -> null")
    public static <T, R> R unwrapSafe(T value, Function<T, R> function) {
        if (value == null) {
            return null;
        }

        return function.apply(value);
    }

    public static <T> String toString(T id) {
        if (id == null) {
            return "<null>";
        }

        return String.valueOf(id);
    }
}
