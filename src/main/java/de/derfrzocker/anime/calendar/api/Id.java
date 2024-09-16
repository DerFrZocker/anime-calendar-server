package de.derfrzocker.anime.calendar.api;

public interface Id {

    int ID_LENGTH = 10;

    IdType idType();

    String id();
}
