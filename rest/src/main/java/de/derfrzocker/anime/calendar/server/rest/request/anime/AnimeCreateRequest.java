package de.derfrzocker.anime.calendar.server.rest.request.anime;

import de.derfrzocker.anime.calendar.server.rest.transfer.anime.AnimeCreateDataTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AnimeCreateRequest(@Valid @NotNull AnimeCreateDataTO anime) {

}
