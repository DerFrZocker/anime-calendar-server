package de.derfrzocker.anime.calendar.anime.api;

import de.derfrzocker.anime.calendar.layer.api.LayerStepConfig;
import java.util.List;

public record AnimeCreateData(String title, int episodeCount, List<LayerStepConfig> episodeLayers) {

}
