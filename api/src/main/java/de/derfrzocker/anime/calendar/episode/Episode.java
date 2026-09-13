package de.derfrzocker.anime.calendar.episode;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;

public record Episode(
        int episodeIndex,
        String episodeName,
        String episodeNumber,
        String episodeNumbers,
        StreamType type,
        String animeListLink,
        URI streamingLink,
        Instant streamingTime,
        Duration episodeLength,
        URI externalLink) {

}
