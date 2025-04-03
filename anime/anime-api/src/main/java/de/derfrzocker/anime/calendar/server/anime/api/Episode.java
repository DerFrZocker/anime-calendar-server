package de.derfrzocker.anime.calendar.server.anime.api;

import java.time.Instant;
import org.jetbrains.annotations.Nullable;

public record Episode(int episodeId, @Nullable String episodeName, @Nullable String episodeNumber,
                      @Nullable String episodeNumbers, @Nullable String type, @Nullable String animeListLink,
                      @Nullable String streamingLink, @Nullable Instant streamingTime, int episodeLength,
                      @Nullable String integrationLink) {
}
