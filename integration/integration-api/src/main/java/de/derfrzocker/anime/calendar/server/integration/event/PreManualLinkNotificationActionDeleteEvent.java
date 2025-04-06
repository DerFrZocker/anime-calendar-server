package de.derfrzocker.anime.calendar.server.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;

public record PreManualLinkNotificationActionDeleteEvent(ManualLinkNotificationAction action,
                                                         RequestContext context) {

}
