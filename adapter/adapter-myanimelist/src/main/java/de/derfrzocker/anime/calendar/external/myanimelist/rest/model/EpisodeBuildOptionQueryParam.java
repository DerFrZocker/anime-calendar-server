package de.derfrzocker.anime.calendar.external.myanimelist.rest.model;

import de.derfrzocker.anime.calendar.anime.Region;
import de.derfrzocker.anime.calendar.anime.StreamType;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.validation.ValidateLanguage;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import org.jspecify.annotations.Nullable;

public record EpisodeBuildOptionQueryParam(
        @QueryParam("region") @Nullable Region region,
        @QueryParam("useRegionName") @Nullable Boolean useRegionName,
        @QueryParam("languagePriorities") @Nullable List<@ValidateLanguage String> languagePriorities,
        @QueryParam("streamType") @Nullable List<StreamType> streamTypes) {

}
