package de.derfrzocker.anime.calendar.anime.layer.transformer;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerTransformer;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.layer.config.StreamingTimeConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Period;

@Identifier(StreamingTimeTransformer.LAYER_TYPE_RAW)
@ApplicationScoped
class StreamingTimeTransformer implements LayerTransformer<StreamingTimeConfig> {

    public static final String LAYER_TYPE_RAW = "streaming_time-transformer";
    public static final LayerType LAYER_TYPE = new LayerType(LAYER_TYPE_RAW);

    @Override
    public void transform(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, StreamingTimeConfig config) {
        if (option.streamTypes().isEmpty()) {
            return;
        }
        if (!option.streamTypes().contains(config.type())) {
            return;
        }

        if (builder.streamType().isPresent() && option.streamTypes().size() > 1) {
            int currentIndex = option.streamTypes().indexOf(builder.streamType().get());
            int potentialIndex = option.streamTypes().indexOf(config.type());

            if (currentIndex < potentialIndex) {
                return;
            }
        }

        Period period = config.period().multipliedBy(builder.index() - config.offset());

        builder.streamingTime(config.startTime().plus(period)).streamType(config.type());
    }
}
