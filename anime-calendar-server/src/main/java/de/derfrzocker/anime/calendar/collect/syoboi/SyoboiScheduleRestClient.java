package de.derfrzocker.anime.calendar.collect.syoboi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json.php")
@RegisterRestClient(configKey = "syoboi-collection")
@RegisterClientHeaders(CollectionHeaderFactory.class)
public interface SyoboiScheduleRestClient {

    @GET
    ScheduleResponse getSchedule(@QueryParam("Req") String req, @QueryParam("start") String start, @QueryParam("days") int days);
}
