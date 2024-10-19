package de.derfrzocker.anime.calendar.web.request.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import java.util.List;

public record AnimePutRequest(List<AnimeId> animeIds) {
}
