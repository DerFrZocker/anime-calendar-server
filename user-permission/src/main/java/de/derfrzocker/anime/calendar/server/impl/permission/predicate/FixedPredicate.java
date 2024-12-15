package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import java.util.function.Predicate;

public record FixedPredicate<T>(boolean value) implements Predicate<T> {

    @Override
    public boolean test(T o) {
        return value();
    }
}
