package de.derfrzocker.anime.calendar.impl.layer;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Episode;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.layer.LayerFilter;
import de.derfrzocker.anime.calendar.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import de.derfrzocker.anime.calendar.api.layer.LayerService;
import de.derfrzocker.anime.calendar.api.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.impl.layer.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.impl.layer.filter.RegionLayerFilter;
import de.derfrzocker.anime.calendar.impl.layer.transformer.EpisodeLengthLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.EpisodeNumberLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.IntegrationUrlLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.NameLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.StreamingTimeLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.StreamingUrlLayer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApplicationScoped
public class LayerServiceImpl implements LayerService {

    private final Map<LayerKey, LayerTransformer<?>> layerTransformers = new HashMap<>();
    private final Map<LayerKey, LayerFilter<?>> layerFilters = new HashMap<>();

    @Override
    public void registerLayer(@NotNull LayerTransformer<?> layer) {
        Objects.requireNonNull(layer, "Cannot register null Layer");

        if (layerTransformers.containsKey(layer.getLayerKey())) {
            throw new IllegalArgumentException("A Layer with the key " + layer.getLayerKey() + " is already registered");
        }

        layerTransformers.put(layer.getLayerKey(), layer);
    }

    @Override
    public void registerLayer(@NotNull LayerFilter<?> layer) {
        Objects.requireNonNull(layer, "Cannot register null Layer");

        if (layerFilters.containsKey(layer.getLayerKey())) {
            throw new IllegalArgumentException("A Layer with the key " + layer.getLayerKey() + " is already registered");
        }

        layerFilters.put(layer.getLayerKey(), layer);
    }

    @Override
    public @Nullable LayerTransformer<?> getLayerTransformer(@NotNull LayerKey layerKey) {
        Objects.requireNonNull(layerKey, "Cannot get Layer from null key");
        return layerTransformers.get(layerKey);
    }

    @Override
    public @Nullable LayerFilter<?> getLayerFilter(@NotNull LayerKey layerKey) {
        Objects.requireNonNull(layerKey, "Cannot get Layer from null key");
        return layerFilters.get(layerKey);
    }

    @Override
    public @NotNull List<@NotNull Episode> transformAnime(@NotNull Anime anime, @NotNull AnimeOptions animeOptions) {
        Objects.requireNonNull(anime, "Cannot transform null Anime");
        Objects.requireNonNull(animeOptions, "Cannot transform with out AnimeOptions");

        List<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < anime.episodeCount(); i++) {
            EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(i);

            anime.transformerData()
                    .stream()
                    .filter(layerHolder -> !layerHolder.shouldSkip(anime, animeOptions, episodeBuilder))
                    .map(LayerHolder::layerDataHolder)
                    .forEach(layerTransformerDataHolder -> layerTransformerDataHolder.transform(anime, animeOptions, episodeBuilder));

            episodes.add(episodeBuilder.build());
        }

        return episodes;
    }

    @PostConstruct
    void registerLayers() {
        registerLayer(EpisodeLengthLayer.INSTANCE);
        registerLayer(EpisodeNumberLayer.INSTANCE);
        registerLayer(NameLayer.INSTANCE);
        registerLayer(StreamingTimeLayer.INSTANCE);
        registerLayer(StreamingUrlLayer.INSTANCE);
        registerLayer(IntegrationUrlLayer.INSTANCE);

        registerLayer(BoundLayerFilter.INSTANCE);
        registerLayer(RegionLayerFilter.INSTANCE);
    }
}
