package de.derfrzocker.anime.calendar.server.model.core;

public record AnimeAccountLinkId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME_ACCOUNT_LINK;
    }
}
