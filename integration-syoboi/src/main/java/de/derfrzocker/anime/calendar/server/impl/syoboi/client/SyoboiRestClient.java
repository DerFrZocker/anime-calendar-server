package de.derfrzocker.anime.calendar.server.impl.syoboi.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json.php")
@RegisterRestClient(configKey = "syoboi-collection")
@RegisterClientHeaders(SyoboiHeaderFactory.class)
public interface SyoboiRestClient {

    @GET
    ScheduleResponse getSchedule(@QueryParam("Req") String req,
                                 @QueryParam("start") String start,
                                 @QueryParam("days") int days);

    @GET
    TitleMediumResponse getTitleMedium(@QueryParam("Req") String req, @QueryParam("TID") String tid);
}
