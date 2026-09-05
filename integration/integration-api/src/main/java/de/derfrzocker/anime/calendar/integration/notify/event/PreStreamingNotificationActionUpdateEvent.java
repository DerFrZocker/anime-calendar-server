package de.derfrzocker.anime.calendar.integration.notify.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionUpdateData;

public record PreStreamingNotificationActionUpdateEvent(StreamingNotificationAction current,
                                                        StreamingNotificationAction updated,
                                                        StreamingNotificationActionUpdateData updateData,
                                                        RequestContext context) {

}
