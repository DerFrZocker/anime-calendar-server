package de.derfrzocker.anime.calendar.server.model.domain.anime;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import java.util.List;

public record AnimeCreateData(String title, int episodeCount, List<LayerHolder> episodeLayers) {

}
