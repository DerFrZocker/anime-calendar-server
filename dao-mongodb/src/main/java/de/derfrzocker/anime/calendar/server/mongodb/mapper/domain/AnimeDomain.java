package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.mongodb.dao.anime.LayerParser;
import de.derfrzocker.anime.calendar.server.mongodb.data.anime.AnimeDO;

public final class AnimeDomain {

    public static AnimeDO toData(Anime domain, LayerParser layerParser) {
        AnimeDO data = new AnimeDO();

        data.id = domain.id();
        data.title = domain.title();
        data.episodeCount = domain.episodeCount();
        data.episodeLayers = layerParser.parse(domain.episodeLayers());
        data.apply(domain);

        return data;
    }

    private AnimeDomain() {

    }
}
