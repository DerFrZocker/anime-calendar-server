package de.derfrzocker.anime.calendar.external.myanimylist.util;

import java.util.Optional;

record FailingCacheEntry<T>(RuntimeException e) implements CacheEntry<T> {

    @Override
    public Optional<T> unwrap() {
        throw e();
    }
}
