package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationAction;

public record PostNewAnimeNotificationActionDeleteEvent(NewAnimeNotificationAction action, RequestContext context) {

}
