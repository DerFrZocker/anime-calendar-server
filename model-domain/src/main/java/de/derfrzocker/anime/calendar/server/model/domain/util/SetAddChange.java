package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.LinkedHashSet;
import java.util.Set;

record SetAddChange<I>(I value) implements Change<Set<I>> {

    @Override
    public Set<I> apply(Set<I> current) {
        Set<I> newSet = new LinkedHashSet<>(current);

        newSet.add(value());

        return newSet;
    }
}
