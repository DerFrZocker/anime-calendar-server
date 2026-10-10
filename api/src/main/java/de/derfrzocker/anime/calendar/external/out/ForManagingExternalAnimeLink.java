package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeLink;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ForManagingExternalAnimeLink {

    Uni<Void> store(ExternalAnimeLink externalAnimeLink);

    Multi<ExternalAnimeLink> retrieveAllWith(Multi<ExternalAnimeId> animeIds);
}
