package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProgramByCountResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TitleMediumAndProgramResponseTDO;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json.php")
@RegisterRestClient(configKey = "syoboi")
public interface TIDDataProviderRestClient {

    @GET
    @ClientQueryParam(name = "Req", value = "TitleMedium,ProgramByDate")
    Optional<TitleMediumAndProgramResponseTDO> getTitleMediumAndProgramms(
            @QueryParam("TID") String tid,
            @QueryParam("days") int days);

    @GET
    @ClientQueryParam(name = "Req", value = "ProgramByCount")
    Optional<ProgramByCountResponseTDO> getProgrammByCount(
            @QueryParam("TID") String tid,
            @QueryParam("Count") String count);
}
