package de.derfrzocker.anime.calendar.api;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record AnimeEpisodes(@NotNull Anime anime, @NotNull List<@NotNull Episode> episodes) {
}
