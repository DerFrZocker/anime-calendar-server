package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionUpdateData;

public record PreManualLinkNotificationActionUpdateEvent(ManualLinkNotificationAction current,
                                                         ManualLinkNotificationAction updated,
                                                         ManualLinkNotificationActionUpdateData updateData,
                                                         RequestContext context) {

}
