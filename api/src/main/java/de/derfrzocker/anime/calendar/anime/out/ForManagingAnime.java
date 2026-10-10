package de.derfrzocker.anime.calendar.anime.out;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.AnimeId;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.Optional;

public interface ForManagingAnime {

    Uni<Optional<Anime>> retrieve(AnimeId id);

    Multi<Anime> retrieveAll(Multi<AnimeId> ids);

    Uni<Void> store(Anime anime);
}
