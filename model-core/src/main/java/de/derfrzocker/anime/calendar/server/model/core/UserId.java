package de.derfrzocker.anime.calendar.server.model.core;

public record UserId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.USER;
    }
}
