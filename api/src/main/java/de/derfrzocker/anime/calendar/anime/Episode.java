package de.derfrzocker.anime.calendar.anime;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface Episode {

    int index();

    AnimeId animeId();

    String name();

    Instant streamingTime();

    Duration duration();

    Optional<String> episode();

    Optional<String> episodes();

    Optional<StreamType> streamType();

    Optional<URI> animeListLink();

    Optional<URI> streamingLink();
}
