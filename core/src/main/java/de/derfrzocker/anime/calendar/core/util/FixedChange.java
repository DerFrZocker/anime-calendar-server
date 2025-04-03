package de.derfrzocker.anime.calendar.core.util;

record FixedChange<T>(T value) implements Change<T> {

    @Override
    public T apply(T current) {
        return value();
    }
}
