package de.derfrzocker.anime.calendar.server.model.domain.util;

public class NoChange<T> implements Change<T> {

    private static final NoChange<?> NO_CHANGE = new NoChange<>();

    public static <T> NoChange<T> noChange() {
        return (NoChange<T>) NO_CHANGE;
    }

    private NoChange() {
    }

    @Override
    public T apply(T current) {
        return current;
    }
}
