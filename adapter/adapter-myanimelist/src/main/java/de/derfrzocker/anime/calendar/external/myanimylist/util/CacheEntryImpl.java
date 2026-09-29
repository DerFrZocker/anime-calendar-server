package de.derfrzocker.anime.calendar.external.myanimylist.util;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

record CacheEntryImpl<T>(@Nullable T entry) implements CacheEntry<T> {

    @Override
    public Optional<T> unwrap() {
        return Optional.ofNullable(entry);
    }
}
