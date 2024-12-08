package de.derfrzocker.anime.calendar.server.model.domain.util;

public class FixedChange<T> implements Change<T> {

    public static <T> FixedChange<T> of(T newValue) {
        return new FixedChange<>(newValue);
    }

    private final T newValue;

    private FixedChange(T newValue) {
        this.newValue = newValue;
    }

    @Override
    public T apply(T current) {
        return this.newValue;
    }
}
