package de.derfrzocker.anime.calendar.api.user;

import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;

public record UserId(String id) implements Id {

    @Override
    public IdType idType() {
        return IdType.USER;
    }
}
