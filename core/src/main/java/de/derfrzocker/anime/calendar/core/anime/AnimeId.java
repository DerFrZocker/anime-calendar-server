package de.derfrzocker.anime.calendar.core.anime;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record AnimeId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'A';

    public AnimeId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
