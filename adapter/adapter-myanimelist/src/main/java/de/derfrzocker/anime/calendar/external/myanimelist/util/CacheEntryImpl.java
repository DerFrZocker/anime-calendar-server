package de.derfrzocker.anime.calendar.external.myanimelist.util;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

record CacheEntryImpl<T>(@Nullable T _entry) implements CacheEntry<T> {

    @Override
    public Optional<T> unwrap() {
        return Optional.ofNullable(_entry);
    }
}
