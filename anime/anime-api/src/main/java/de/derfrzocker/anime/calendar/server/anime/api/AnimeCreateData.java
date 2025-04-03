package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerHolder;
import java.util.List;

public record AnimeCreateData(String title, int episodeCount, List<LayerHolder> episodeLayers) {

}
