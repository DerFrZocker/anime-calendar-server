package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.client.MyAnimeListRestClient;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.MyAnimeListStatus;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.SimpleRestResponseHolder;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.UserListDataTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.UserListNodeTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.UserListResponseTDO;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
public class IntegrationUserRestDaoService {

    @RestClient
    MyAnimeListRestClient restClient;

    @CacheResult(cacheName = "integration-myanimelist-rest-client-user-list-anime-ids")
    public SimpleRestResponseHolder<Set<IntegrationAnimeId>> getAnimeIds(
            @CacheKey IntegrationUserId user,
            @CacheKey MyAnimeListStatus status,
            @CacheKey int limit,
            RequestContext context) {
        RestResponse<UserListResponseTDO> response = this.restClient.getUserAnimes(user.raw(), status, limit);
        if (response.getStatus() != RestResponse.Status.OK.getStatusCode()) {
            return new SimpleRestResponseHolder<>(response.getStatus(), null);
        }

        UserListResponseTDO responseEntity = response.getEntity();
        Set<IntegrationAnimeId> animeIds = new HashSet<>();
        if (responseEntity != null && responseEntity.data() != null) {
            responseEntity
                    .data()
                    .stream()
                    .map(UserListDataTDO::node)
                    .filter(Objects::nonNull)
                    .map(UserListNodeTDO::id)
                    .map(String::valueOf)
                    .map(IntegrationAnimeId::new)
                    .forEach(animeIds::add);
        }

        return new SimpleRestResponseHolder<>(response.getStatus(), animeIds);
    }
}
