package de.derfrzocker.anime.calendar.server.model.core;

public interface Id {

    int ID_LENGTH = 10;

    IdType idType();

    String raw();
}
