package de.derfrzocker.anime.calendar.impl.layer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Episode;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.layer.Layer;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import de.derfrzocker.anime.calendar.api.layer.LayerService;
import de.derfrzocker.anime.calendar.impl.layer.layer.BaseStreamingTimeLayer;
import de.derfrzocker.anime.calendar.impl.layer.layer.EpisodeLengthLayer;
import de.derfrzocker.anime.calendar.impl.layer.layer.EpisodeNumberLayer;
import de.derfrzocker.anime.calendar.impl.layer.layer.RegionNameLayer;
import de.derfrzocker.anime.calendar.impl.layer.layer.RegionStreamingTimeLayer;
import de.derfrzocker.anime.calendar.impl.layer.layer.RegionStreamingUrlLayer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class LayerServiceImpl implements LayerService {

    private final Map<LayerKey, Layer<?>> layers = new HashMap<>();

    @Override
    public void registerLayer(@NotNull Layer<?> layer) {
        Objects.requireNonNull(layer, "Cannot register null Layer");

        if (layers.containsKey(layer.getLayerKey())) {
            throw new IllegalArgumentException("A Layer with the key " + layer.getLayerKey() + " is already registered");
        }

        layers.put(layer.getLayerKey(), layer);
    }

    @Override
    public @Nullable Layer<?> getLayer(@NotNull LayerKey layerKey) {
        Objects.requireNonNull(layerKey, "Cannot get Layer from null key");
        return layers.get(layerKey);
    }

    @Override
    public @NotNull List<@NotNull Episode> transformAnime(@NotNull Anime anime, @NotNull AnimeOptions animeOptions) {
        Objects.requireNonNull(anime, "Cannot transform null Anime");
        Objects.requireNonNull(animeOptions, "Cannot transform with out AnimeOptions");

        List<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < anime.episodeCount(); i++) {
            EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(i);

            anime.transformerData().forEach(transformerData -> transformerData.transform(anime, animeOptions, episodeBuilder));

            episodes.add(episodeBuilder.build());
        }

        return episodes;
    }

    @PostConstruct
    void registerLayers() {
        registerLayer(BaseStreamingTimeLayer.INSTANCE);
        registerLayer(EpisodeLengthLayer.INSTANCE);
        registerLayer(EpisodeNumberLayer.INSTANCE);
        registerLayer(RegionNameLayer.INSTANCE);
        registerLayer(RegionStreamingTimeLayer.INSTANCE);
        registerLayer(RegionStreamingUrlLayer.INSTANCE);
    }
}
