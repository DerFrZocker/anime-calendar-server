package de.derfrzocker.anime.calendar.server.rest.request.calendar;

import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarAnimeLinkCreateOrUpdateDataTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CalendarAnimeLinkCreateOrUpdateRequest(@Valid @NotNull CalendarAnimeLinkCreateOrUpdateDataTO calendarAnimeLink) {

}
