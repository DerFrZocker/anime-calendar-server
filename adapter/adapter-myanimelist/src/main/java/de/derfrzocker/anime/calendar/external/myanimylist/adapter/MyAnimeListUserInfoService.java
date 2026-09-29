package de.derfrzocker.anime.calendar.external.myanimylist.adapter;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import de.derfrzocker.anime.calendar.external.myanimylist.client.MyAnimeListRestClient;
import de.derfrzocker.anime.calendar.external.myanimylist.config.MyAnimeListConfig;
import de.derfrzocker.anime.calendar.external.myanimylist.exception.NotAuthorizedException;
import de.derfrzocker.anime.calendar.external.myanimylist.exception.NotFoundException;
import de.derfrzocker.anime.calendar.external.myanimylist.exception.UnknownException;
import de.derfrzocker.anime.calendar.external.myanimylist.model.UserListDataTDO;
import de.derfrzocker.anime.calendar.external.myanimylist.model.UserListNodeTDO;
import de.derfrzocker.anime.calendar.external.myanimylist.model.UserListResponseTDO;
import de.derfrzocker.anime.calendar.external.myanimylist.util.CacheEntry;
import de.derfrzocker.anime.calendar.external.out.ForFetchingExternalUserInfo;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collection;
import java.util.List;

@ApplicationScoped
class MyAnimeListUserInfoService implements ForFetchingExternalUserInfo {

    private static final String WATCHING = "watching";
    private static final String PLAN_TO_WATCH = "plan_to_watch";

    private final MyAnimeListRestClient restClient;
    private final MyAnimeListConfig config;

    @Inject
    MyAnimeListUserInfoService(@RestClient MyAnimeListRestClient restClient, MyAnimeListConfig config) {
        this.restClient = restClient;
        this.config = config;
    }

    @Override
    public Uni<List<ExternalAnimeId>> fetchWatchingAnime(ExternalUserId externalUserId) {
        return fetchWatchingAnime(externalUserId.raw()).map(CacheEntry::unwrap)
                .map(opt -> opt.orElseThrow(() -> new IllegalStateException(
                        "No config entry present. This is a bug in the code.")));
    }

    @CacheResult(cacheName = "adapter-myanimelist-fetch_watching_anime")
    Uni<CacheEntry<List<ExternalAnimeId>>> fetchWatchingAnime(String username) {
        return Uni.join()
                .all(
                        restClient.fetchUserAnimes(username, WATCHING, config.userInfoFetchSize()),
                        restClient.fetchUserAnimes(username, PLAN_TO_WATCH, config.userInfoFetchSize()))
                // We only want to fetch it one at a time to not unnecessarily call the API twice on 404 / 403 status.
                .usingConcurrencyOf(1)
                .andFailFast()
                .map(this::toDomain)
                .map(CacheEntry::of)
                .onFailure(WebApplicationException.class)
                .transform(this::toKnownException)
                .onFailure()
                .recoverWithItem(this::toFailingCacheEntry);
    }

    private List<ExternalAnimeId> toDomain(List<UserListResponseTDO> data) {
        return data.stream()
                .map(UserListResponseTDO::data)
                .flatMap(Collection::stream)
                .map(UserListDataTDO::node)
                .map(UserListNodeTDO::id)
                .distinct()
                .map(ExternalAnimeId::new)
                .toList();
    }

    private Throwable toKnownException(WebApplicationException webException) {
        Response.Status status = webException.getResponse().getStatusInfo().toEnum();
        if (status == Response.Status.NOT_FOUND) {
            return new NotFoundException(
                    "Could not find MyAnimeList information for the given user. Make sure the user name ist spelled correctly.",
                    "MyAnimeList user was not found.");
        }
        if (status == Response.Status.FORBIDDEN) {
            return new NotAuthorizedException(
                    "Could not access MyAnimeList information for the given user. Make sure your currently watching and planed to watch lists are public.",
                    "MyAnimeList user is private or auth client does no longer work.");
        }

        return webException;
    }

    private <T> CacheEntry<T> toFailingCacheEntry(Throwable cause) {
        RuntimeException runtimeCause = switch (cause) {
            case NotFoundException e -> e;
            case NotAuthorizedException e -> e;
            default -> new UnknownException("Unknown exception during MyAnimeList rest call.", cause);
        };

        return CacheEntry.failing(runtimeCause);
    }
}
