package de.derfrzocker.anime.calendar.external.myanimelist.util;

import java.util.Optional;

record FailingCacheEntry<T>(RuntimeException e) implements CacheEntry<T> {

    @Override
    public Optional<T> unwrap() {
        throw e();
    }
}
