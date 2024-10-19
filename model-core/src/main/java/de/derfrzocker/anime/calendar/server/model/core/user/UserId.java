package de.derfrzocker.anime.calendar.server.model.core.user;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record UserId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.USER;
    }
}
