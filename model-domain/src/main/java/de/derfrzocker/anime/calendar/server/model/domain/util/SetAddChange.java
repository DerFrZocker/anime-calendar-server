package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetAddChange<I, T extends Set<I>> implements Change<T> {

    private final I item;

    public SetAddChange(I item) {
        this.item = item;
    }

    @Override
    public T apply(T current) {
        Set<I> newSet = new LinkedHashSet<>(current);

        newSet.add(item);

        return (T) newSet;
    }
}
