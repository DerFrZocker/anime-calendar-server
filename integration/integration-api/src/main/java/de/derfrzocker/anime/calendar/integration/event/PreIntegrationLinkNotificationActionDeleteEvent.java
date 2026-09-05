package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;

public record PreIntegrationLinkNotificationActionDeleteEvent(IntegrationLinkNotificationAction action,
                                                              RequestContext context) {

}
