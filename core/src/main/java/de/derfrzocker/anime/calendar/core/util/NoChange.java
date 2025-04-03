package de.derfrzocker.anime.calendar.core.util;

final class NoChange<T> implements Change<T> {

    private static final NoChange<?> INSTANCE = new NoChange<>();

    static <T> Change<T> get() {
        return (Change<T>) INSTANCE;
    }

    private NoChange() {
    }

    @Override
    public T apply(T current) {
        return current;
    }
}
