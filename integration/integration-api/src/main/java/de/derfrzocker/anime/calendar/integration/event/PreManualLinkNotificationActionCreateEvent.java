package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionCreateData;

public record PreManualLinkNotificationActionCreateEvent(ManualLinkNotificationAction action,
                                                         ManualLinkNotificationActionCreateData createData,
                                                         RequestContext context) {

}
