package de.derfrzocker.anime.calendar.anime.layer.transformer;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerTransformer;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.LocalizedNameConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@Identifier(LocalizedNameTransformer.LAYER_TYPE_RAW)
@ApplicationScoped
class LocalizedNameTransformer implements LayerTransformer<LocalizedNameConfig> {

    public static final String LAYER_TYPE_RAW = "localized_name-transformer";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    @Override
    public void transform(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, LocalizedNameConfig config) {
        if (option.languagePriorities().isEmpty()) {
            return;
        }
        if (!option.languagePriorities().contains(config.language())) {
            return;
        }

        if (builder.nameLanguage().isPresent() && option.languagePriorities().size() > 1) {
            int currentIndex = option.languagePriorities().indexOf(builder.nameLanguage().get());
            int potentialIndex = option.languagePriorities().indexOf(config.language());

            if (currentIndex < potentialIndex) {
                return;
            }
        }

        builder.name(config.name()).nameLanguage(config.language());
    }
}
