package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreIntegrationLinkNotificationActionCreateEvent(IntegrationLinkNotificationAction action,
                                                              IntegrationLinkNotificationActionCreateData createData,
                                                              RequestContext context) {

}
