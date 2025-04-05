package de.derfrzocker.anime.calendar.server.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;

public record PostNewAnimeNotificationActionCreateEvent(NewAnimeNotificationAction action,
                                                        NewAnimeNotificationActionCreateData createData,
                                                        RequestContext context) {

}
