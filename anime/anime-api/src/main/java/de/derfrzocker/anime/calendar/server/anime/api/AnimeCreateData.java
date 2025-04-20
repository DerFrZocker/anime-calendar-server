package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.server.layer2.api.LayerStepConfig;
import java.util.List;

public record AnimeCreateData(String title, int episodeCount, List<LayerStepConfig> episodeLayers) {

}
