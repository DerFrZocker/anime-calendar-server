package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.ArrayList;
import java.util.List;

public final class ChangeBuilder<T> {

    public static <T> ChangeBuilder<T> builder() {
        return new ChangeBuilder<>();
    }

    private final List<Change<T>> changes = new ArrayList<>();

    private ChangeBuilder() {

    }

    public ChangeBuilder<T> add(Change<T> change) {
        this.changes.add(change);

        return this;
    }

    public Change<T> build() {
        return new MultiChange<>(new ArrayList<>(this.changes));
    }

    private record MultiChange<T>(List<Change<T>> changes) implements Change<T> {

        @Override
        public T apply(T current) {
            for (Change<T> change : changes()) {
                current = change.apply(current);
            }

            return current;
        }
    }
}
