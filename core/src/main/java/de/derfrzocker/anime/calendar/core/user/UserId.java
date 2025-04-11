package de.derfrzocker.anime.calendar.core.user;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record UserId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'U';

    public UserId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
