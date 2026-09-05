package de.derfrzocker.anime.calendar.anime.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationActionUpdateData;

public record PostNewAnimeNotificationActionUpdateEvent(NewAnimeNotificationAction current,
                                                        NewAnimeNotificationAction updated,
                                                        NewAnimeNotificationActionUpdateData updateData,
                                                        RequestContext context) {

}
