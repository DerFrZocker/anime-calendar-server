package de.derfrzocker.anime.calendar.server.model.core.user;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record UserId(String raw) implements Id {

    public static final char ID_PREFIX = 'U';

    @Override
    public IdType idType() {
        return IdType.USER;
    }
}
