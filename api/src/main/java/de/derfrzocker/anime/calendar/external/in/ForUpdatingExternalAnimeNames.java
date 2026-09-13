package de.derfrzocker.anime.calendar.external.in;

import de.derfrzocker.anime.calendar.RequestContext;
import io.smallrye.mutiny.Uni;

public interface ForUpdatingExternalAnimeNames {

    Uni<Void> updateAnimeNames(RequestContext context);
}
