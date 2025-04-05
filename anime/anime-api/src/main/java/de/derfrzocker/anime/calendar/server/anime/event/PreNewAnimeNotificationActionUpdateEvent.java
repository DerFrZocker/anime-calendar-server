package de.derfrzocker.anime.calendar.server.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionUpdateData;

public record PreNewAnimeNotificationActionUpdateEvent(NewAnimeNotificationAction current,
                                                       NewAnimeNotificationAction updated,
                                                       NewAnimeNotificationActionUpdateData updateData,
                                                       RequestContext context) {

}
