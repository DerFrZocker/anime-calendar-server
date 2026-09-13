package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeNameHolder;
import de.derfrzocker.anime.calendar.external.ExternalId;
import de.derfrzocker.anime.calendar.external.NameLanguage;
import de.derfrzocker.anime.calendar.external.NameType;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

public interface ForManagingExternalAnimeNames {

    Uni<Optional<ExternalAnimeNameHolder>> getBy(
            ExternalId externalId,
            ExternalAnimeId animeId,
            NameType type,
            NameLanguage language,
            RequestContext context);

    Uni<Void> insert(ExternalAnimeNameHolder animeName, RequestContext context);

    Uni<Void> update(ExternalAnimeNameHolder animeName, RequestContext context);
}
