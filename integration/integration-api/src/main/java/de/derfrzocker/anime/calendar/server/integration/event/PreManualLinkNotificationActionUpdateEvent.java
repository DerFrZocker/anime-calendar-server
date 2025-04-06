package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionUpdateData;

public record PreManualLinkNotificationActionUpdateEvent(ManualLinkNotificationAction current,
                                                         ManualLinkNotificationAction updated,
                                                         ManualLinkNotificationActionUpdateData updateData,
                                                         RequestContext context) {

}
