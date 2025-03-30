package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.core.user.UserId;

public record CalendarCreateData(UserId owner, String name) {

}
