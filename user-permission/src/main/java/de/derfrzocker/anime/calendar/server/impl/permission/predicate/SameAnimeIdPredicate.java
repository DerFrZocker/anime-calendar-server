package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import java.util.Objects;
import java.util.function.Predicate;

public record SameAnimeIdPredicate(AnimeId id) implements Predicate<AnimeId> {

    @Override
    public boolean test(AnimeId toTest) {
        return Objects.equals(id(), toTest);
    }
}
