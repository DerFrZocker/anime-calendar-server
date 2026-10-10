package de.derfrzocker.anime.calendar.anime;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface EpisodeBuilder {

    EpisodeBuilder name(String name);

    EpisodeBuilder duration(Duration duration);

    EpisodeBuilder episode(String episode);

    EpisodeBuilder episodes(String episode);

    EpisodeBuilder streamType(StreamType streamType);

    EpisodeBuilder animeListLink(URI animeListLink);

    EpisodeBuilder streamingLink(URI streamingLink);

    EpisodeBuilder streamingTime(Instant streamingTime);

    Optional<Episode> buildIfValid();
}
