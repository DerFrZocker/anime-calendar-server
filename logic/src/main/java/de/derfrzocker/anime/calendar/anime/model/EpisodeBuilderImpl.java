package de.derfrzocker.anime.calendar.anime.model;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.anime.Episode;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.StreamType;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public class EpisodeBuilderImpl implements EpisodeBuilder {

    private final int index;
    private final AnimeId animeId;
    private String name;
    @Nullable
    private Duration duration;
    @Nullable
    private String episode;
    @Nullable
    private String episodes;
    @Nullable
    private StreamType streamType;
    @Nullable
    private URI animeListLink;
    @Nullable
    private URI streamingLink;
    @Nullable
    private Instant streamingTime;

    public static EpisodeBuilder of(int index, AnimeId animeId, String name) {
        return new EpisodeBuilderImpl(index, animeId, name);
    }

    private EpisodeBuilderImpl(int index, AnimeId animeId, String name) {
        this.index = index;
        this.animeId = Objects.requireNonNull(animeId);
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public EpisodeBuilder name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    @Override
    public EpisodeBuilder duration(Duration duration) {
        this.duration = Objects.requireNonNull(duration);
        return this;
    }

    @Override
    public EpisodeBuilder episode(String episode) {
        this.episode = Objects.requireNonNull(episode);
        return this;
    }

    @Override
    public EpisodeBuilder episodes(String episode) {
        this.episodes = Objects.requireNonNull(episode);
        return this;
    }

    @Override
    public EpisodeBuilder streamType(StreamType streamType) {
        this.streamType = Objects.requireNonNull(streamType);
        return this;
    }

    @Override
    public EpisodeBuilder animeListLink(URI animeListLink) {
        this.animeListLink = Objects.requireNonNull(animeListLink);
        return this;
    }

    @Override
    public EpisodeBuilder streamingLink(URI streamingLink) {
        this.streamingLink = Objects.requireNonNull(streamingLink);
        return this;
    }

    @Override
    public EpisodeBuilder streamingTime(Instant streamingTime) {
        this.streamingTime = Objects.requireNonNull(streamingTime);
        return this;
    }

    @Override
    public Optional<Episode> buildIfValid() {

        if (streamingTime == null) {
            return Optional.empty();
        }

        if (duration == null) {
            return Optional.empty();
        }

        return Optional.of(new EpisodeImpl(
                index,
                animeId,
                name,
                streamingTime,
                duration,
                episode,
                episodes,
                streamType,
                animeListLink,
                streamingLink));
    }

    private record EpisodeImpl(
            int index,
            AnimeId animeId,
            String name,
            Instant streamingTime,
            Duration duration,
            @Nullable String _episode,
            @Nullable String _episodes,
            @Nullable StreamType _streamType,
            @Nullable URI _animeListLink,
            @Nullable URI _streamingLink) implements Episode {

        public EpisodeImpl {
            Objects.requireNonNull(animeId);
            Objects.requireNonNull(name);
            Objects.requireNonNull(streamingTime);
            Objects.requireNonNull(duration);
        }

        @Override
        public Optional<String> episode() {
            return Optional.ofNullable(_episode);
        }

        @Override
        public Optional<String> episodes() {
            return Optional.ofNullable(_episodes);
        }

        @Override
        public Optional<StreamType> streamType() {
            return Optional.ofNullable(_streamType);
        }

        @Override
        public Optional<URI> animeListLink() {
            return Optional.ofNullable(_animeListLink);
        }

        @Override
        public Optional<URI> streamingLink() {
            return Optional.ofNullable(_streamingLink);
        }
    }
}
