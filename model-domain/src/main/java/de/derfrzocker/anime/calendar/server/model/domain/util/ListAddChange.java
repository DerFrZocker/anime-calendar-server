package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.ArrayList;
import java.util.List;

public class ListAddChange<I> implements Change<List<I>> {

    private final I item;

    public ListAddChange(I item) {
        this.item = item;
    }

    @Override
    public List<I> apply(List<I> current) {
        List<I> newList = new ArrayList<>(current);

        newList.add(item);

        return newList;
    }
}
