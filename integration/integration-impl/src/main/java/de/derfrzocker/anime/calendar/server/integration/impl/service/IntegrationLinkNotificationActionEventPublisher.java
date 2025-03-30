package de.derfrzocker.anime.calendar.server.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.event.PostIntegrationLinkNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostIntegrationLinkNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostIntegrationLinkNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreIntegrationLinkNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreIntegrationLinkNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreIntegrationLinkNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class IntegrationLinkNotificationActionEventPublisher {

    @Inject
    Event<PreIntegrationLinkNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostIntegrationLinkNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreIntegrationLinkNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostIntegrationLinkNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreIntegrationLinkNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostIntegrationLinkNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(IntegrationLinkNotificationAction action,
                              IntegrationLinkNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreIntegrationLinkNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(IntegrationLinkNotificationAction action,
                               IntegrationLinkNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostIntegrationLinkNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(IntegrationLinkNotificationAction current,
                              IntegrationLinkNotificationAction updated,
                              IntegrationLinkNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreIntegrationLinkNotificationActionUpdateEvent(current,
                                                                                     updated,
                                                                                     updateData,
                                                                                     context));
    }

    public void firePostUpdate(IntegrationLinkNotificationAction current,
                               IntegrationLinkNotificationAction updated,
                               IntegrationLinkNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostIntegrationLinkNotificationActionUpdateEvent(current,
                                                                                       updated,
                                                                                       updateData,
                                                                                       context));
    }

    public void firePreDelete(IntegrationLinkNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreIntegrationLinkNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(IntegrationLinkNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostIntegrationLinkNotificationActionDeleteEvent(action, context));
    }
}
