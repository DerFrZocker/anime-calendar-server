package de.derfrzocker.anime.calendar.server.episode.api;

import java.time.Instant;
import org.jetbrains.annotations.Nullable;

public record Episode(int episodeId, @Nullable String episodeName, @Nullable String episodeNumber,
                      @Nullable String episodeNumbers, @Nullable StreamType type, @Nullable String animeListLink,
                      @Nullable String streamingLink, @Nullable Instant streamingTime, int episodeLength,
                      @Nullable String integrationLink) {

}
