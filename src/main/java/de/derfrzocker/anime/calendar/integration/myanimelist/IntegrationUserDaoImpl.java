package de.derfrzocker.anime.calendar.integration.myanimelist;

import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.integration.Integrations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Named(Integrations.MY_ANIME_LIST)
class IntegrationUserDaoImpl implements IntegrationUserDao {

    @RestClient
    IntegrationUserRestClient integrationUserRestClient;

    @Override
    public Set<IntegrationAnimeId> getUserIds(IntegrationUserId user) {
        IntegrationUserListResponse watching = integrationUserRestClient.getAnimeList(user.id(), Status.watching);
        IntegrationUserListResponse planToWatch = integrationUserRestClient.getAnimeList(user.id(), Status.plan_to_watch);

        Set<IntegrationAnimeId> userIds = new HashSet<>();
        userIds.addAll(parseResponse(watching));
        userIds.addAll(parseResponse(planToWatch));

        return userIds;
    }

    private Collection<IntegrationAnimeId> parseResponse(IntegrationUserListResponse watching) {
        List<IntegrationAnimeId> userIds = new ArrayList<>();

        for (IntegrationUserListResponse.Data data : watching.data()) {
            userIds.add(new IntegrationAnimeId(String.valueOf(data.node().id())));
        }

        return userIds;
    }
}