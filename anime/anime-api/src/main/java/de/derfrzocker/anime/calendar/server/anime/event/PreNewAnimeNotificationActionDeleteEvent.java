package de.derfrzocker.anime.calendar.server.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;

public record PreNewAnimeNotificationActionDeleteEvent(NewAnimeNotificationAction action, RequestContext context) {

}
