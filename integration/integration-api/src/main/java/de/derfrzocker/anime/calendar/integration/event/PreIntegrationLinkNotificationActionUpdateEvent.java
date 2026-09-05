package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationActionUpdateData;

public record PreIntegrationLinkNotificationActionUpdateEvent(IntegrationLinkNotificationAction current,
                                                              IntegrationLinkNotificationAction updated,
                                                              IntegrationLinkNotificationActionUpdateData updateData,
                                                              RequestContext context) {

}
