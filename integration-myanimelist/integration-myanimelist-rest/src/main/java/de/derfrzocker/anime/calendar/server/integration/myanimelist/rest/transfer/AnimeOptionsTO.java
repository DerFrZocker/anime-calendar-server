package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.transfer;

import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain.ValidateStreamType;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import java.util.List;

public record AnimeOptionsTO(@QueryParam("region") @DefaultValue("DE_DE") Region region,
                             @QueryParam("useRegionName") @DefaultValue("true") boolean useRegionName,
                             @QueryParam("streamType") List<@ValidateStreamType StreamType> streamTypes) {

}
