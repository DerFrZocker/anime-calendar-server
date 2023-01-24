package de.derfrzocker.anime.calendar.api.dao;

import de.derfrzocker.anime.calendar.api.Anime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AnimeDao {

    @Nullable
    Anime getAnime(int id);

    @NotNull
    List<@NotNull Integer> getAnimeIds(@NotNull String userId);

    void saveAnimeIds(@NotNull String userId, @NotNull List<@NotNull Integer> integers);
}
