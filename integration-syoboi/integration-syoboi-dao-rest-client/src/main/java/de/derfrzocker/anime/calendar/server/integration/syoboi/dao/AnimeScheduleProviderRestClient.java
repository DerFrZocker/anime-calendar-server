package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ScheduleResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.header.SyoboiHeaderFactory;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json.php")
@RegisterRestClient(configKey = "syoboi-collection")
@RegisterClientHeaders(SyoboiHeaderFactory.class)
public interface AnimeScheduleProviderRestClient {

    @GET
    @ClientQueryParam(name = "Req", value = "ProgramByDate")
    Optional<ScheduleResponseTDO> getProgramByDate(@QueryParam("start") String start, @QueryParam("days") int days);
}
