package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationActionCreateData;

public record PreIntegrationLinkNotificationActionCreateEvent(IntegrationLinkNotificationAction action,
                                                              IntegrationLinkNotificationActionCreateData createData,
                                                              RequestContext context) {

}
