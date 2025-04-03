package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerHolder;
import java.util.List;

public record AnimeUpdateData(Change<String> title, Change<Integer> episodeCount,
                              Change<List<LayerHolder>> episodeLayers) {

    public static AnimeUpdateData toEpisode(int episodeCount) {
        return new AnimeUpdateData(Change.nothing(), Change.to(episodeCount), Change.nothing());
    }

    public static AnimeUpdateData addingLayer(LayerHolder layer) {
        return new AnimeUpdateData(Change.nothing(), Change.nothing(), Change.addingToList(layer));
    }
}
