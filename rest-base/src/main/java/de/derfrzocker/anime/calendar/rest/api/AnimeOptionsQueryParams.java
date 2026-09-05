package de.derfrzocker.anime.calendar.rest.api;

import de.derfrzocker.anime.calendar.anime.api.Region;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.episode.api.StreamType;
import de.derfrzocker.anime.calendar.rest.constrain.ValidateLanguage;
import de.derfrzocker.anime.calendar.rest.constrain.ValidateStreamType;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import java.util.List;

public record AnimeOptionsQueryParams(@QueryParam("region") @DefaultValue("DE_DE") Region region,
                                      @QueryParam("useRegionName") @DefaultValue("true") boolean useRegionName,
                                      @QueryParam("languagePriorities") List<@ValidateLanguage String> languagePriorities,
                                      @QueryParam("streamType") List<@ValidateStreamType StreamType> streamTypes) {

    // TODO 2025-05-10: Should this be really here?
    public AnimeOptionsBuilder toBuilder() {
        Region region = Region.DEFAULT_REGION;
        if (region() != null) {
            region = region();
        }

        AnimeOptionsBuilder builder = AnimeOptionsBuilder.anAnimeOptions(region)
                                                         .withUseRegionName(useRegionName());

        if (!streamTypes().isEmpty()) {
            builder.withStreamTypes(streamTypes());
        }

        if (languagePriorities() != null) {
            builder.withLanguagePriorities(languagePriorities());
        }

        return builder;
    }
}
