package de.derfrzocker.anime.calendar.server.model.domain.permission;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.core.util.Change;

public record UserPermissionUpdateData(Change<ObjectPermission<UserId>> user,
                                       Change<ObjectPermission<CalendarId>> calendar,
                                       Change<ObjectPermission<AnimeId>> anime,
                                       Change<ObjectPermission<CalendarId>> calendarAnimeLink) {

}
