package de.derfrzocker.anime.calendar.server.provider;

import de.derfrzocker.anime.calendar.server.Anime;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.List;

public interface AnimeUserInfoProvider {

    String getProviderName();

    @NotNull
    List<String> getAnimeId(@NotNull String userId);

    URI createAnimeLink(Anime anime);
}
