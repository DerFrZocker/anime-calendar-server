package de.derfrzocker.anime.calendar.server.model.domain.util;

final class NullChange<T> implements Change<T> {

    private static final NullChange<?> INSTANCE = new NullChange<>();

    static <T> Change<T> get() {
        return (Change<T>) INSTANCE;
    }

    @Override
    public T apply(T t) {
        return null;
    }
}
