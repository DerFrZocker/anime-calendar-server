package de.derfrzocker.anime.calendar.server.model.domain.layer;

import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.List;

public record LayerHolder(List<LayerFilterDataHolder<?>> filters, LayerTransformerDataHolder<?> layerDataHolder) {

    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, EpisodeBuilder episodeBuilder) {
        return filters.stream().anyMatch(filter -> filter.shouldSkip(anime, animeOptions, episodeBuilder));
    }
}
