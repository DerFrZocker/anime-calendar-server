package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import java.util.List;
import java.util.function.Predicate;

public record OrPredicate<T>(List<Predicate<T>> predicates) implements Predicate<T> {

    @Override
    public boolean test(T t) {
        return predicates().stream().anyMatch(predicate -> predicate.test(t));
    }
}
