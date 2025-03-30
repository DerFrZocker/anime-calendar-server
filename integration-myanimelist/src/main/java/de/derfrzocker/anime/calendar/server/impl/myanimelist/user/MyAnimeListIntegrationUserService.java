package de.derfrzocker.anime.calendar.server.impl.myanimelist.user;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListRestClient;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListStatus;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListUserListResponse;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class MyAnimeListIntegrationUserService {

    @RestClient
    MyAnimeListRestClient restClient;

    @CacheResult(cacheName = "my-anime-list-integration-user-rest-call")
    public Set<IntegrationAnimeId> getAnimeIds(IntegrationUserId userId) {
        MyAnimeListUserListResponse watching = this.restClient.getUserAnimes(userId.raw(),
                                                                             MyAnimeListStatus.watching,
                                                                             true);
        MyAnimeListUserListResponse planToWatch = this.restClient.getUserAnimes(userId.raw(),
                                                                                MyAnimeListStatus.plan_to_watch,
                                                                                true);

        Set<IntegrationAnimeId> userIds = new HashSet<>();
        userIds.addAll(parseResponse(watching));
        userIds.addAll(parseResponse(planToWatch));

        return userIds;
    }

    private Collection<IntegrationAnimeId> parseResponse(MyAnimeListUserListResponse watching) {
        List<IntegrationAnimeId> userIds = new ArrayList<>();

        for (MyAnimeListUserListResponse.Data data : watching.data()) {
            userIds.add(new IntegrationAnimeId(String.valueOf(data.node().id())));
        }

        return userIds;
    }
}
