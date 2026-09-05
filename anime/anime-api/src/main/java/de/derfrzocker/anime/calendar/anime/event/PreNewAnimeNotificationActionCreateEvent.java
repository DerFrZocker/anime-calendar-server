package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationActionCreateData;

public record PreNewAnimeNotificationActionCreateEvent(NewAnimeNotificationAction action,
                                                       NewAnimeNotificationActionCreateData createData,
                                                       RequestContext context) {

}
