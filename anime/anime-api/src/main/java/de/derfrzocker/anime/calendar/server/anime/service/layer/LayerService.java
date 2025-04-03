package de.derfrzocker.anime.calendar.server.anime.service.layer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.Episode;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerTransformer;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
