package de.derfrzocker.anime.calendar.integration.syoboi.impl.holder;

import de.derfrzocker.anime.calendar.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ProvidedAnimeSchedule;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ResolvedTIDData;
import java.util.List;

public record AnimeScheduleHolder(ProvidedAnimeSchedule schedule, ResolvedTIDData tidData,
                                  List<AnimeIntegrationLink> links) {

}
