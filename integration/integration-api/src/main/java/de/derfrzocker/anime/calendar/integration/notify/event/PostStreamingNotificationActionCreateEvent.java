package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionCreateData;

public record PostStreamingNotificationActionCreateEvent(StreamingNotificationAction action,
                                                         StreamingNotificationActionCreateData createData,
                                                         RequestContext context) {

}
