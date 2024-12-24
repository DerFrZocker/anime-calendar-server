package de.derfrzocker.anime.calendar.server.model.domain.util;

record FixedChange<T>(T value) implements Change<T> {

    @Override
    public T apply(T current) {
        return value();
    }
}
