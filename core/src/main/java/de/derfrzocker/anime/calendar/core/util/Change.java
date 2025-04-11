package de.derfrzocker.anime.calendar.core.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Change<T> extends Function<T, T> {

    @NotNull
    static <T> Change<T> toNull() {
        return NullChange.get();
    }

    @NotNull
    static <T> Change<T> nothing() {
        return NoChange.get();
    }

    @NotNull
    static <T> Change<T> to(@Nullable T value) {
        return new FixedChange<>(value);
    }

    @NotNull
    static <I> Change<List<I>> addingToList(@Nullable I value) {
        return new ListAddChange<>(value);
    }

    @NotNull
    static <I> Change<Set<I>> addingToSet(@Nullable I value) {
        return new SetAddChange<>(value);
    }

    @NotNull
    static <K, V> Change<Map<K, V>> addingToMap(@Nullable K key, @Nullable V value) {
        return new MapAddChange<>(key, value);
    }
}
