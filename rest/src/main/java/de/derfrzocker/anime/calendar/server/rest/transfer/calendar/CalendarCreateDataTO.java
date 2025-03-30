package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateUserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CalendarCreateDataTO(@ValidateUserId UserId owner, @NotBlank @Size(max = 255) String name) {

}
