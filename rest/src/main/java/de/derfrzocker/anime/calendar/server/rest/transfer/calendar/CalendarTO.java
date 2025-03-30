package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.user.UserId;

public record CalendarTO(CalendarId id, CalendarKey key, UserId owner, String name) {

}
