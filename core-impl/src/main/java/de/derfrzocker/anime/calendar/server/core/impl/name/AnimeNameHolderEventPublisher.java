package de.derfrzocker.anime.calendar.server.core.impl.name;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostAnimeNameHolderDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostAnimeNameHolderUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PreAnimeNameHolderCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PreAnimeNameHolderDeleteEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PreAnimeNameHolderUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
public class AnimeNameHolderEventPublisher {

    @Inject
    Event<PreAnimeNameHolderCreateEvent> preCreateEvent;
    @Inject
    Event<PostAnimeNameHolderCreateEvent> postCreateEvent;

    @Inject
    Event<PreAnimeNameHolderUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostAnimeNameHolderUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreAnimeNameHolderDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostAnimeNameHolderDeleteEvent> postDeleteEvent;

    public void firePreCreateEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeNameHolderCreateData createData,
                                   AnimeNameHolder animeNameHolder,
                                   RequestContext context) {

        this.preCreateEvent.fire(new PreAnimeNameHolderCreateEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   createData,
                                                                   animeNameHolder,
                                                                   context));
    }

    public void firePostCreateEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    AnimeNameHolderCreateData createData,
                                    AnimeNameHolder animeNameHolder,
                                    RequestContext context) {

        this.postCreateEvent.fire(new PostAnimeNameHolderCreateEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     createData,
                                                                     animeNameHolder,
                                                                     context));
    }

    public void firePreUpdateEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeNameHolderUpdateData updateData,
                                   AnimeNameHolder current,
                                   AnimeNameHolder updated,
                                   RequestContext context) {

        this.preUpdateEvent.fire(new PreAnimeNameHolderUpdateEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   updateData,
                                                                   current,
                                                                   updated,
                                                                   context));
    }

    public void firePostUpdateEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    AnimeNameHolderUpdateData updateData,
                                    AnimeNameHolder current,
                                    AnimeNameHolder updated,
                                    RequestContext context) {

        this.postUpdateEvent.fire(new PostAnimeNameHolderUpdateEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     updateData,
                                                                     current,
                                                                     updated,
                                                                     context));
    }

    public void firePreDeleteEvent(IntegrationId integrationId,
                                   IntegrationAnimeId integrationAnimeId,
                                   AnimeNameHolder animeNameHolder,
                                   RequestContext context) {

        this.preDeleteEvent.fire(new PreAnimeNameHolderDeleteEvent(integrationId,
                                                                   integrationAnimeId,
                                                                   animeNameHolder,
                                                                   context));
    }

    public void firePostDeleteEvent(IntegrationId integrationId,
                                    IntegrationAnimeId integrationAnimeId,
                                    AnimeNameHolder animeNameHolder,
                                    RequestContext context) {

        this.postDeleteEvent.fire(new PostAnimeNameHolderDeleteEvent(integrationId,
                                                                     integrationAnimeId,
                                                                     animeNameHolder,
                                                                     context));
    }
}
