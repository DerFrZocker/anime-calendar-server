package de.derfrzocker.anime.calendar.server.core.impl.season;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PostAnimeSeasonInfoCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PostAnimeSeasonInfoDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PostAnimeSeasonInfoUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PreAnimeSeasonInfoCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PreAnimeSeasonInfoDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.season.PreAnimeSeasonInfoUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@RequestScoped
public class AnimeSeasonInfoEventPublisher {

    @Inject
    Event<PreAnimeSeasonInfoCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeSeasonInfoCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeSeasonInfoUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeSeasonInfoUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeSeasonInfoDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeSeasonInfoDeleteEvent> postDeleteEvent;

    public void firePreCreateEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   int year,
                                   Season season,
                                   AnimeSeasonInfoCreateData createData,
                                   AnimeSeasonInfo animeSeasonInfo,
                                   RequestContext context) {

        this.preCreateEvent.fire(new PreAnimeSeasonInfoCreateEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   year,
                                                                   season,
                                                                   createData,
                                                                   animeSeasonInfo,
                                                                   context));
    }

    public void firePostCreateEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    int year,
                                    Season season,
                                    AnimeSeasonInfoCreateData createData,
                                    AnimeSeasonInfo animeSeasonInfo,
                                    RequestContext context) {

        this.postCreateEvent.fire(new PostAnimeSeasonInfoCreateEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     year,
                                                                     season,
                                                                     createData,
                                                                     animeSeasonInfo,
                                                                     context));
    }

    public void firePreUpdateEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   int year,
                                   Season season,
                                   AnimeSeasonInfoUpdateData updateData,
                                   AnimeSeasonInfo current,
                                   AnimeSeasonInfo updated,
                                   RequestContext context) {

        this.preUpdateEvent.fire(new PreAnimeSeasonInfoUpdateEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   year,
                                                                   season,
                                                                   updateData,
                                                                   current,
                                                                   updated,
                                                                   context));
    }

    public void firePostUpdateEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    int year,
                                    Season season,
                                    AnimeSeasonInfoUpdateData updateData,
                                    AnimeSeasonInfo current,
                                    AnimeSeasonInfo updated,
                                    RequestContext context) {

        this.postUpdateEvent.fire(new PostAnimeSeasonInfoUpdateEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     year,
                                                                     season,
                                                                     updateData,
                                                                     current,
                                                                     updated,
                                                                     context));
    }

    public void firePreDeleteEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   int year,
                                   Season season,
                                   AnimeSeasonInfo animeSeasonInfo,
                                   RequestContext context) {

        this.preDeleteEvent.fire(new PreAnimeSeasonInfoDeleteEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   year,
                                                                   season,
                                                                   animeSeasonInfo,
                                                                   context));
    }

    public void firePostDeleteEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    int year,
                                    Season season,
                                    AnimeSeasonInfo animeSeasonInfo,
                                    RequestContext context) {

        this.postDeleteEvent.fire(new PostAnimeSeasonInfoDeleteEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     year,
                                                                     season,
                                                                     animeSeasonInfo,
                                                                     context));
    }
}
