package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.core.user.UserId;

public record UserToken(String raw) {

    public static final int TOKEN_KEY_LENGTH = UserId.ID_LENGTH + 30;
    public static final char TOKEN_PREFIX_CHAR = 'T';

    public UserId userId() {
        return UserId.of(raw().substring(0, UserId.ID_LENGTH));
    }
}
