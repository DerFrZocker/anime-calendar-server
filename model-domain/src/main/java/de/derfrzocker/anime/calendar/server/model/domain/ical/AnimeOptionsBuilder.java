package de.derfrzocker.anime.calendar.server.model.domain.ical;

public final class AnimeOptionsBuilder {

    private final Region region;
    private boolean useRegionName = true;

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

    public AnimeOptionsBuilder but() {
        return anAnimeOptions(region).withUseRegionName(useRegionName);
    }

    public AnimeOptions build() {
        return new AnimeOptions(region, useRegionName, null);
    }
}
