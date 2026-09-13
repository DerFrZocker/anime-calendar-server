package de.derfrzocker.anime.calendar.util;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface Change<T extends @Nullable Object> extends Function<T, T> {

    static <T> Change<@Nullable T> toNull() {
        return ignore -> null;
    }

    static <T extends @Nullable Object> Change<T> nothing() {
        return value -> value;
    }

    static <T extends @Nullable Object> Change<T> to(T value) {
        return ignore -> value;
    }

    static <I extends @Nullable Object> Change<List<I>> addingToList(I value) {
        return new ListAddChange<>(value);
    }

    static <I extends @Nullable Object> Change<Set<I>> addingToSet(I value) {
        return new SetAddChange<>(value);
    }

    static <K, V extends @Nullable Object> Change<Map<K, V>> addingToMap(K key, V value) {
        return new MapAddChange<>(key, value);
    }

    static <T extends @Nullable Object> ChangeBuilder<T> builder() {
        return ChangeBuilder.builder();
    }
}
