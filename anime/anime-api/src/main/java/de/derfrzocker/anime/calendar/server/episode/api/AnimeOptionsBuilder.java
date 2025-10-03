package de.derfrzocker.anime.calendar.server.episode.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AnimeOptionsBuilder {

    private final Region region;
    private boolean useRegionName = true;
    private List<String> languagePriorities = new ArrayList<>();
    private IntegrationId integrationId;
    private List<StreamType> streamTypes = List.of(StreamType.SUB, StreamType.ORG);

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

    public AnimeOptionsBuilder withLanguagePriorities(List<String> languagePriorities) {
        this.languagePriorities = languagePriorities;
        return this;
    }

    public AnimeOptionsBuilder withLanguagePriorities(String... languagePriorities) {
        this.languagePriorities = Arrays.asList(languagePriorities);
        return this;
    }

    public AnimeOptionsBuilder withIntegrationId(IntegrationId integrationId) {
        this.integrationId = integrationId;
        return this;
    }

    public AnimeOptionsBuilder withStreamTypes(List<StreamType> streamTypes) {
        this.streamTypes = streamTypes;
        return this;
    }

    public AnimeOptionsBuilder withStreamTypes(StreamType... streamTypes) {
        this.streamTypes = List.of(streamTypes);
        return this;
    }

    public AnimeOptions build() {
        return new AnimeOptions(this.region, this.useRegionName, this.languagePriorities, this.integrationId,
                                this.streamTypes);
    }
}
