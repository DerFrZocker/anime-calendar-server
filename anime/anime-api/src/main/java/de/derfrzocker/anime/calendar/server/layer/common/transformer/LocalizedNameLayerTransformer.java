package de.derfrzocker.anime.calendar.server.layer.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.config.LocalizedNameLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class LocalizedNameLayerTransformer extends AbstractLayerTransformer<LocalizedNameLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("localized-name");
    public static final LocalizedNameLayerTransformer INSTANCE = new LocalizedNameLayerTransformer();

    private LocalizedNameLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull LocalizedNameLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        if (animeOptions.languagePriorities().isEmpty()) {
            return;
        }
        if (!animeOptions.languagePriorities().contains(layerConfig.language())) {
            return;
        }
        if (episodeBuilder.episodeNameLanguage() != null && animeOptions.languagePriorities().size() > 1) {
            int currentIndex = animeOptions.languagePriorities().indexOf(episodeBuilder.episodeNameLanguage());
            int potentialIndex = animeOptions.languagePriorities().indexOf(layerConfig.language());

            if (currentIndex < potentialIndex) {
                return;
            }
        }

        episodeBuilder.withEpisodeName(layerConfig.name());
        episodeBuilder.withEpisodeNameLanguage(layerConfig.language());
    }

    @Override
    protected Class<LocalizedNameLayerConfig> configClass() {
        return LocalizedNameLayerConfig.class;
    }
}
