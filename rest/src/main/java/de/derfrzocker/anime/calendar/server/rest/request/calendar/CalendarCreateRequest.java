package de.derfrzocker.anime.calendar.server.rest.request.calendar;

import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarCreateDataTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CalendarCreateRequest(@Valid @NotNull CalendarCreateDataTO calendar) {

}
