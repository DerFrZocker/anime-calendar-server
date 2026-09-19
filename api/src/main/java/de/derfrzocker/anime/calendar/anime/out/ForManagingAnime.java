package de.derfrzocker.anime.calendar.anime.out;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.AnimeId;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

public interface ForManagingAnime {

    Uni<Optional<Anime>> retrieve(AnimeId id);

    Uni<Void> store(Anime anime);
}
