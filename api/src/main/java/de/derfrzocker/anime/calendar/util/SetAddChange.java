package de.derfrzocker.anime.calendar.util;

import org.jspecify.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

record SetAddChange<I extends @Nullable Object>(I value) implements Change<Set<I>> {

    @Override
    public Set<I> apply(Set<I> current) {
        Set<I> newSet = new LinkedHashSet<>(current);

        newSet.add(value());

        return newSet;
    }
}
