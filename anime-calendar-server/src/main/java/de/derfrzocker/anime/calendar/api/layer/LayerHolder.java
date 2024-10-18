package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import java.util.List;

public record LayerHolder(List<LayerFilterDataHolder<?>> filters, LayerTransformerDataHolder<?> layerDataHolder) {

    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, EpisodeBuilder episodeBuilder) {
        return filters.stream().anyMatch(filter -> filter.shouldSkip(anime, animeOptions, episodeBuilder));
    }
}
