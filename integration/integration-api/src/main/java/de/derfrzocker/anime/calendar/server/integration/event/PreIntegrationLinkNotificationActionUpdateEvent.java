package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PreIntegrationLinkNotificationActionUpdateEvent(IntegrationLinkNotificationAction current,
                                                              IntegrationLinkNotificationAction updated,
                                                              IntegrationLinkNotificationActionUpdateData updateData,
                                                              RequestContext context) {

}
