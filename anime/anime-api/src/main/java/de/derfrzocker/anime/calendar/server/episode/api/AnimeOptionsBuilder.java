package de.derfrzocker.anime.calendar.server.episode.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Region;

public final class AnimeOptionsBuilder {

    private final Region region;
    private boolean useRegionName = true;
    private IntegrationId integrationId;
    private String streamType;

    private AnimeOptionsBuilder(Region region) {
        this.region = region;
    }

    public static AnimeOptionsBuilder anAnimeOptions(Region region) {
        return new AnimeOptionsBuilder(region);
    }

    public AnimeOptionsBuilder withUseRegionName(boolean useRegionName) {
        this.useRegionName = useRegionName;
        return this;
    }

    public AnimeOptionsBuilder withIntegrationId(IntegrationId integrationId) {
        this.integrationId = integrationId;
        return this;
    }

    public AnimeOptionsBuilder withStreamType(String streamType) {
        this.streamType = streamType;
        return this;
    }

    public AnimeOptionsBuilder but() {
        return anAnimeOptions(region).withUseRegionName(useRegionName);
    }

    public AnimeOptions build() {
        return new AnimeOptions(region, useRegionName, integrationId, streamType);
    }
}
