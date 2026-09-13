package de.derfrzocker.anime.calendar.util;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChangeBuilder<T extends @Nullable Object> {

    static <T extends @Nullable Object> ChangeBuilder<T> builder() {
        return new ChangeBuilder<>();
    }

    private final List<Change<T>> changes = new ArrayList<>();

    private ChangeBuilder() {

    }

    public ChangeBuilder<T> add(Change<T> change) {
        Objects.requireNonNull(change);

        this.changes.add(change);

        return this;
    }

    public Change<T> build() {
        return new MultiChange<>(copyChanges());
    }

    private List<Change<T>> copyChanges() {
        return new ArrayList<>(this.changes);
    }

    private record MultiChange<T extends @Nullable Object>(List<Change<T>> changes) implements Change<T> {

        @Override
        public T apply(T current) {
            for (Change<T> change : changes()) {
                current = change.apply(current);
            }

            return current;
        }
    }
}
