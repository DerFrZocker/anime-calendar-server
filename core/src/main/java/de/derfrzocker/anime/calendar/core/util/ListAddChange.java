package de.derfrzocker.anime.calendar.core.util;

import java.util.ArrayList;
import java.util.List;

record ListAddChange<I>(I value) implements Change<List<I>> {

    @Override
    public List<I> apply(List<I> current) {
        List<I> newList = new ArrayList<>(current);

        newList.add(value());

        return newList;
    }
}
