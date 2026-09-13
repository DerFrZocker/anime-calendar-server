package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ForFetchingExternalUserAnimes {

    Uni<List<ExternalAnimeId>> fetchForUser(ExternalUserId externalUserId);
}
