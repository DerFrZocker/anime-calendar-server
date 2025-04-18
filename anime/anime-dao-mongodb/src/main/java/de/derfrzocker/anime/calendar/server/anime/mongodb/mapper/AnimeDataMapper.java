package de.derfrzocker.anime.calendar.server.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.AnimeDO;
import de.derfrzocker.anime.calendar.server.anime.mongodb.parser.LayerParser;

public final class AnimeDataMapper {

    private AnimeDataMapper() {
    }

    public static AnimeDO toData(Anime domain, LayerParser layerParser) {
        AnimeDO data = new AnimeDO();

        data.id = domain.id();
        data.title = domain.title();
        data.episodeCount = domain.episodeCount();
        data.episodeLayers = layerParser.parse(domain.episodeLayers());
        data.apply(domain);

        return data;
    }

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
                         layerParser.createLayerHolder(data.episodeLayers),
                         null);
    }
}
