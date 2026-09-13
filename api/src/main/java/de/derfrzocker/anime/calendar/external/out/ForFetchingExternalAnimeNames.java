package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.RequestContext;
import io.smallrye.mutiny.Multi;

public interface ForFetchingExternalAnimeNames {

    Multi<FetchedExternalAnimeNameHolder> fetchAnimeNames(RequestContext context);
}
