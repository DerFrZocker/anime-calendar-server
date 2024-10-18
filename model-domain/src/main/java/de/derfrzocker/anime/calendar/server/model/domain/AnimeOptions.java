package de.derfrzocker.anime.calendar.server.model.domain;

import org.jetbrains.annotations.NotNull;

public record AnimeOptions(@NotNull Region region, boolean useRegionName, String streamType) {
}
