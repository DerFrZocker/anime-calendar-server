package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionUpdateData;

public record PostIntegrationLinkNotificationActionUpdateEvent(IntegrationLinkNotificationAction current,
                                                               IntegrationLinkNotificationAction updated,
                                                               IntegrationLinkNotificationActionUpdateData updateData,
                                                               RequestContext context) {

}
