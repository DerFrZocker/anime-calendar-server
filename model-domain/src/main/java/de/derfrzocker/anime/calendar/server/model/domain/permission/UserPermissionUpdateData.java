package de.derfrzocker.anime.calendar.server.model.domain.permission;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;

public record UserPermissionUpdateData(Change<ObjectPermission<UserId>> user,
                                       Change<ObjectPermission<CalendarId>> calendar,
                                       Change<ObjectPermission<AnimeId>> anime,
                                       Change<ObjectPermission<CalendarId>> calendarAnimeLink) {

}
