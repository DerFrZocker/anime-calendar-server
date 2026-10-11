package de.derfrzocker.anime.calendar.anime.layer.transformer;

import de.derfrzocker.anime.calendar.anime.LayerConfig;
import de.derfrzocker.anime.calendar.anime.StreamType;
import de.derfrzocker.anime.calendar.anime.layer.config.EmptyConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.ExternalUriConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.LocalizedNameConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleDurationConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleOffsetIntegerConfig;
import de.derfrzocker.anime.calendar.anime.layer.config.StreamingTimeConfig;
import de.derfrzocker.anime.calendar.external.ExternalType;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;

public final class Transformers {

    private Transformers() {
    }

    public static LayerConfig<ExternalUriConfig> animeListLink(ExternalType animeListType, URI uri) {
        return new LayerConfig<>(
                AnimeListLinkTransformer.LAYER_TYPE,
                ExternalUriConfig.DATA_TYPE,
                new ExternalUriConfig(animeListType, uri));
    }

    public static LayerConfig<SimpleDurationConfig> duration(Duration duration) {
        return new LayerConfig<>(
                DurationTransformer.LAYER_TYPE,
                SimpleDurationConfig.DATA_TYPE,
                new SimpleDurationConfig(duration));
    }

    public static LayerConfig<EmptyConfig> episodes() {
        return new LayerConfig<>(EpisodesTransformer.LAYER_TYPE, EmptyConfig.DATA_TYPE, EmptyConfig.EMPTY);
    }

    public static LayerConfig<SimpleOffsetIntegerConfig> episode(int episode, int offset) {
        return new LayerConfig<>(
                EpisodeTransformer.LAYER_TYPE,
                SimpleOffsetIntegerConfig.DATA_TYPE,
                new SimpleOffsetIntegerConfig(episode, offset));
    }

    public static LayerConfig<LocalizedNameConfig> localizedName(String language, String name) {
        return new LayerConfig<>(
                LocalizedNameTransformer.LAYER_TYPE,
                LocalizedNameConfig.DATA_TYPE,
                new LocalizedNameConfig(language, name));
    }

    public static LayerConfig<ExternalUriConfig> streamingLink(ExternalType streamingServiceType, URI uri) {
        return new LayerConfig<>(
                StreamingLinkTransformer.LAYER_TYPE,
                ExternalUriConfig.DATA_TYPE,
                new ExternalUriConfig(streamingServiceType, uri));
    }

    public static LayerConfig<StreamingTimeConfig> streamingTime(
            Instant startTime,
            Period period,
            int offset,
            StreamType streamType) {

        return new LayerConfig<>(
                StreamingTimeTransformer.LAYER_TYPE,
                StreamingTimeConfig.DATA_TYPE,
                new StreamingTimeConfig(startTime, period, offset, streamType));
    }
}
