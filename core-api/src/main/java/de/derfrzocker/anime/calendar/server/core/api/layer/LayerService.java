package de.derfrzocker.anime.calendar.server.core.api.layer;

import de.derfrzocker.anime.calendar.server.model.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.Episode;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformer;
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
