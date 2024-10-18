package de.derfrzocker.anime.calendar.server.model.domain;

import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public record AnimeEpisodes(@NotNull Anime anime, @NotNull List<@NotNull Episode> episodes) {
}
