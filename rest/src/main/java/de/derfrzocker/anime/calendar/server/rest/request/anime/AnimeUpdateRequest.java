package de.derfrzocker.anime.calendar.server.rest.request.anime;

import de.derfrzocker.anime.calendar.server.rest.transfer.anime.AnimeUpdateDataTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AnimeUpdateRequest(@Valid @NotNull AnimeUpdateDataTO anime) {

}
