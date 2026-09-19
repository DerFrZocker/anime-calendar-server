package de.derfrzocker.anime.calendar.anime;

import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public final class EpisodeBuilder {

    private final int index;
    private String name;
    @Nullable
    private Duration length;
    @Nullable
    private String episode;
    @Nullable
    private String episodes;
    @Nullable
    private URI animeListLink;
    @Nullable
    private URI streamingLink;
    @Nullable
    private Instant streamingTime;

    public static EpisodeBuilder of(int index, String name) {
        return new EpisodeBuilder(index, name);
    }

    private EpisodeBuilder(int index, String name) {
        this.index = index;
        this.name = Objects.requireNonNull(name);
    }

    public EpisodeBuilder name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    public EpisodeBuilder length(Duration length) {
        this.length = length;
        return this;
    }

    public EpisodeBuilder episode(String episode) {
        this.episode = episode;
        return this;
    }

    public EpisodeBuilder episodes(String episode) {
        this.episodes = episode;
        return this;
    }

    public EpisodeBuilder animeListLink(URI animeListLink) {
        this.animeListLink = animeListLink;
        return this;
    }

    public EpisodeBuilder streamingLink(URI streamingLink) {
        this.streamingLink = streamingLink;
        return this;
    }

    public EpisodeBuilder streamingTime(Instant streamingTime) {
        this.streamingTime = streamingTime;
        return this;
    }

    public Episode build() {

        return new Episode(index, name, length, episode, episodes, animeListLink, streamingLink, streamingTime);
    }
}
