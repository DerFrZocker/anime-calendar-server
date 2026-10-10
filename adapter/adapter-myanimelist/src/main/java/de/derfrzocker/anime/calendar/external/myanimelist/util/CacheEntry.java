package de.derfrzocker.anime.calendar.external.myanimelist.util;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface CacheEntry<T> {

    static <T> CacheEntry<T> of(@Nullable T value) {
        return new CacheEntryImpl<>(value);
    }

    static <T> CacheEntry<T> failing(@Nullable RuntimeException value) {
        return new FailingCacheEntry<>(value);
    }

    Optional<T> unwrap();
}
