package de.derfrzocker.anime.calendar.server.model.domain.util;

public interface Change<T> {

    T apply(T current);
}
