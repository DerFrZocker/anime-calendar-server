package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.model.domain.util.SetAddChange;
import java.util.Set;

public record UserUpdateData(Change<Set<CalendarId>> calendars) {

    public static UserUpdateData addCalendar(CalendarId calendarId) {
        return new UserUpdateData(new SetAddChange<>(calendarId));
    }
}
