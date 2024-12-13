package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.mongodb.dao.anime.LayerParser;
import de.derfrzocker.anime.calendar.server.mongodb.data.anime.AnimeDO;

public final class AnimeData {

    public static Anime toDomain(AnimeDO data, LayerParser layerParser) {
        String title = data.title;
        if (title == null) {
            title = data.animeTitle;
        }

        return new Anime(data.id,
                         data.createdAt,
                         data.createdBy,
                         data.updatedAt,
                         data.updatedBy,
                         title,
                         data.episodeCount,
                         layerParser.createLayerHolder(data.episodeLayers));
    }

    private AnimeData() {

    }
}
