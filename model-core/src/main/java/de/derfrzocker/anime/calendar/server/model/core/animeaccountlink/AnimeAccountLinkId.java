package de.derfrzocker.anime.calendar.server.model.core.animeaccountlink;

public record AnimeAccountLinkId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'L';
}
