package de.derfrzocker.anime.calendar.integration.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;

public record PostManualLinkNotificationActionDeleteEvent(ManualLinkNotificationAction action,
                                                          RequestContext context) {

}
