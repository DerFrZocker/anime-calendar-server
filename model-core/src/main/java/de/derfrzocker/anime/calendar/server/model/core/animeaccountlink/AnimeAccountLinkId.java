package de.derfrzocker.anime.calendar.server.model.core.animeaccountlink;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record AnimeAccountLinkId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME_ACCOUNT_LINK;
    }
}
