package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.exception.ActionNotAllowedException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.exception.UnexpectedException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.integration.dao.IntegrationUserDao;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.config.MyAnimeListRestClientConfig;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.MyAnimeListStatus;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.SimpleRestResponseHolder;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.service.IntegrationUserRestDaoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
@Named("myanimelist" + IntegrationUserDao.NAME_SUFFIX)
public class IntegrationUserRestDaoImpl implements IntegrationUserDao {

    private static final String NOT_FOUND = "Could not find MyAnimeList information for the given user. Make sure the user name ist spelled correctly.";
    private static final String NOT_ALLOWED = "Could not access MyAnimeList information for the given user. Make sure your currently watching and planed to watch lists are public.";
    private static final String UNEXPECTED = "Could not get MyAnimeList information for the given user, because of unexpected status '%s'.";

    @Inject
    IntegrationUserRestDaoService service;
    @Inject
    MyAnimeListRestClientConfig config;

    @Override
    public Set<IntegrationAnimeId> getAnimeIds(IntegrationUserId user, RequestContext context) {
        Set<IntegrationAnimeId> animeIds = new HashSet<>();
        Optional.of(this.service.getAnimeIds(user,
                                             MyAnimeListStatus.watching,
                                             this.config.userListSizeLimit(),
                                             context)).map(this::unpack).ifPresent(animeIds::addAll);
        Optional.of(this.service.getAnimeIds(user,
                                             MyAnimeListStatus.plan_to_watch,
                                             this.config.userListSizeLimit(),
                                             context)).map(this::unpack).ifPresent(animeIds::addAll);

        return animeIds;
    }

    private Set<IntegrationAnimeId> unpack(SimpleRestResponseHolder<Set<IntegrationAnimeId>> holder) {
        if (holder.status() == RestResponse.Status.OK.getStatusCode()) {
            return holder.data();
        }

        if (holder.status() == RestResponse.Status.NOT_FOUND.getStatusCode()) {
            throw ResourceNotFoundException.from(NOT_FOUND).get();
        }

        if (holder.status() == RestResponse.Status.FORBIDDEN.getStatusCode()) {
            throw ActionNotAllowedException.from(NOT_ALLOWED).get();
        }

        throw UnexpectedException.from(UNEXPECTED.formatted(holder.status())).get();
    }
}
