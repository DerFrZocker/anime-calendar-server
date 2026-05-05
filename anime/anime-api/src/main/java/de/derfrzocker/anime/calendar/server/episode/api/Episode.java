package de.derfrzocker.anime.calendar.server.episode.api;

import java.time.Instant;

public record Episode(
        int episodeId,
        String episodeName,
        String episodeNumber,
        String episodeNumbers,
        StreamType type,
        String animeListLink,
        String streamingLink,
        Instant streamingTime,
        int episodeLength,
        String integrationLink) {

}
