package de.derfrzocker.anime.calendar.server.model.domain;

import org.jetbrains.annotations.NotNull;

public final class AnimeOptionsBuilder {
    private @NotNull
    final Region region;
    private boolean useRegionName = true;

    private AnimeOptionsBuilder(@NotNull Region region) {
        this.region = region;
    }

    public static AnimeOptionsBuilder anAnimeOptions(@NotNull Region region) {
        return new AnimeOptionsBuilder(region);
    }

    public AnimeOptionsBuilder withUseRegionName(boolean useRegionName) {
        this.useRegionName = useRegionName;
        return this;
    }

    public AnimeOptionsBuilder but() {
        return anAnimeOptions(region).withUseRegionName(useRegionName);
    }

    public AnimeOptions build() {
        return new AnimeOptions(region, useRegionName, null);
    }
}
