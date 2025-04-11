package de.derfrzocker.anime.calendar.core.animeaccountlink;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record AnimeAccountLinkId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'L';

    public AnimeAccountLinkId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
