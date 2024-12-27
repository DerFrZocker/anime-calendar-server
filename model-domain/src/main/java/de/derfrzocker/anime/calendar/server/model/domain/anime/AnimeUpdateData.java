package de.derfrzocker.anime.calendar.server.model.domain.anime;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
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
