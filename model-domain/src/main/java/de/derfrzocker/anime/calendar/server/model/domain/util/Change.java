package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface Change<T> extends Function<T, T> {

    static <T> Change<T> toNull() {
        return NullChange.get();
    }

    static <T> Change<T> nothing() {
        return NoChange.get();
    }

    static <T> Change<T> to(T value) {
        return new FixedChange<>(value);
    }

    static <I> Change<List<I>> addingToList(I value) {
        return new ListAddChange<>(value);
    }

    static <I> Change<Set<I>> addingToSet(I value) {
        return new SetAddChange<>(value);
    }

    static <K, V> Change<Map<K, V>> addingToMap(K key, V value) {
        return new MapAddChange<>(key, value);
    }
}
