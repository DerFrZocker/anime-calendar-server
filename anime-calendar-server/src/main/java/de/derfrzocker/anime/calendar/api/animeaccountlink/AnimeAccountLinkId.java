package de.derfrzocker.anime.calendar.api.animeaccountlink;

import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;

public record AnimeAccountLinkId(String id) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME_ACCOUNT_LINK;
    }
}
