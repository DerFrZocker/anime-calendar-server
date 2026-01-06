package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.core.user.UserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CalendarCreateDataTO(UserId owner, @NotBlank @Size(max = 255) String name) {

}
