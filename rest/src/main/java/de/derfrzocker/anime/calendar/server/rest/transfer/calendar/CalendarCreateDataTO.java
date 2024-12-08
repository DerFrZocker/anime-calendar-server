package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CalendarCreateDataTO(@ValidateId UserId owner, @NotBlank @Size(max = 255) String name) {

}
