package de.derfrzocker.anime.calendar.server;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

public record Episode(int episodeId, int episodeNumber, int length, @Nullable LocalDateTime airingTime,
                      Map<Region, RegionStream> regionStreams) {
}
