package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByTIDAndChIDResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ScheduleResponseTDO;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "syoboi")
public interface AnimeScheduleProviderRestClient {

    @GET
    @Path("/json.php")
    @ClientQueryParam(name = "Req", value = "ProgramByDate")
    Optional<ScheduleResponseTDO> getProgramByDate(@QueryParam("start") String start, @QueryParam("days") int days);

    @GET
    @Path("/db.php")
    @ClientQueryParam(name = "Command", value = "ProgLookup")
    @Produces(MediaType.TEXT_XML)
    ProgramByTIDAndChIDResponseTDO getProgrammByTIDAndChID(
            @QueryParam("TID") String tid,
            @QueryParam("ChID") String channelId);
}
