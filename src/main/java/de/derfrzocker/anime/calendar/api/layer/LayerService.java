package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Episode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface LayerService {

    void registerLayer(@NotNull LayerTransformer<?> layer);

    void registerLayer(@NotNull LayerFilter<?> layerFilter);

    @Nullable
    LayerTransformer<?> getLayerTransformer(@NotNull LayerKey layerKey);

    @Nullable
    LayerFilter<?> getLayerFilter(@NotNull LayerKey layerKey);

    @NotNull
    List<Episode> transformAnime(@NotNull Anime anime, @NotNull AnimeOptions animeOptions);
}
