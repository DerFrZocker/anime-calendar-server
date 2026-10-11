package de.derfrzocker.anime.calendar.anime;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface EpisodeBuilder {

    int index();

    EpisodeBuilder name(String name);

    EpisodeBuilder nameLanguage(String language);

    Optional<String> nameLanguage();

    EpisodeBuilder duration(Duration duration);

    EpisodeBuilder episode(String episode);

    EpisodeBuilder episodes(String episode);

    EpisodeBuilder streamType(StreamType streamType);

    Optional<StreamType> streamType();

    EpisodeBuilder animeListLink(URI animeListLink);

    EpisodeBuilder streamingLink(URI streamingLink);

    EpisodeBuilder streamingTime(Instant streamingTime);

    Optional<Episode> buildIfValid();
}
