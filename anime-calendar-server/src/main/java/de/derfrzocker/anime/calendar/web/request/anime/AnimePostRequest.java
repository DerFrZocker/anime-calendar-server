package de.derfrzocker.anime.calendar.web.request.anime;

import java.util.List;
import java.util.Map;

public record AnimePostRequest(String animeTitle, int episodeCount, List<Map<String, Object>> episodeLayers) {

}
