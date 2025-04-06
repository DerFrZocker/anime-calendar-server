package de.derfrzocker.anime.calendar.server.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.event.PostManualLinkNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostManualLinkNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PostManualLinkNotificationActionUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreManualLinkNotificationActionCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreManualLinkNotificationActionDeleteEvent;
import de.derfrzocker.anime.calendar.server.integration.event.PreManualLinkNotificationActionUpdateEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
class ManualLinkNotificationActionEventPublisher {

    @Inject
    Event<PreManualLinkNotificationActionCreateEvent> preCreateEvent;
    @Inject
    Event<PostManualLinkNotificationActionCreateEvent> postCreateEvent;

    @Inject
    Event<PreManualLinkNotificationActionUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostManualLinkNotificationActionUpdateEvent> postUpdateEvent;

    @Inject
    Event<PreManualLinkNotificationActionDeleteEvent> preDeleteEvent;
    @Inject
    Event<PostManualLinkNotificationActionDeleteEvent> postDeleteEvent;

    public void firePreCreate(ManualLinkNotificationAction action,
                              ManualLinkNotificationActionCreateData createData,
                              RequestContext context) {
        this.preCreateEvent.fire(new PreManualLinkNotificationActionCreateEvent(action, createData, context));
    }

    public void firePostCreate(ManualLinkNotificationAction action,
                               ManualLinkNotificationActionCreateData createData,
                               RequestContext context) {
        this.postCreateEvent.fire(new PostManualLinkNotificationActionCreateEvent(action, createData, context));
    }

    public void firePreUpdate(ManualLinkNotificationAction current,
                              ManualLinkNotificationAction updated,
                              ManualLinkNotificationActionUpdateData updateData,
                              RequestContext context) {
        this.preUpdateEvent.fire(new PreManualLinkNotificationActionUpdateEvent(current, updated, updateData, context));
    }

    public void firePostUpdate(ManualLinkNotificationAction current,
                               ManualLinkNotificationAction updated,
                               ManualLinkNotificationActionUpdateData updateData,
                               RequestContext context) {
        this.postUpdateEvent.fire(new PostManualLinkNotificationActionUpdateEvent(current,
                                                                                  updated,
                                                                                  updateData,
                                                                                  context));
    }

    public void firePreDelete(ManualLinkNotificationAction action, RequestContext context) {
        this.preDeleteEvent.fire(new PreManualLinkNotificationActionDeleteEvent(action, context));
    }

    public void firePostDelete(ManualLinkNotificationAction action, RequestContext context) {
        this.postDeleteEvent.fire(new PostManualLinkNotificationActionDeleteEvent(action, context));
    }
}
