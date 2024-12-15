package de.derfrzocker.anime.calendar.server.rest.transfer.anime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AnimeCreateDataTO(@NotBlank @Size(max = 255) String title, @Positive int episodeCount) {

}
