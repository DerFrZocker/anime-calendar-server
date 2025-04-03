package de.derfrzocker.anime.calendar.server.anime.api.layer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import java.util.List;

public record LayerHolder(List<LayerFilterDataHolder<?>> filters, LayerTransformerDataHolder<?> layerDataHolder) {

    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, EpisodeBuilder episodeBuilder) {
        return filters.stream().anyMatch(filter -> filter.shouldSkip(anime, animeOptions, episodeBuilder));
    }
}
