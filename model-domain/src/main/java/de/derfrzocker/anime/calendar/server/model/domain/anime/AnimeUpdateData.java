package de.derfrzocker.anime.calendar.server.model.domain.anime;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import java.util.List;

public record AnimeUpdateData(Change<String> title, Change<Integer> episodeCount,
                              Change<List<LayerHolder>> episodeLayers) {

}
