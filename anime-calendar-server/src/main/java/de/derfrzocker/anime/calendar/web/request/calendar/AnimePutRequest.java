package de.derfrzocker.anime.calendar.web.request.calendar;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import java.util.List;

public record AnimePutRequest(List<AnimeId> animeIds) {
}
