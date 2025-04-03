package de.derfrzocker.anime.calendar.server.anime.impl.generator;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.generator.AbstractIdGenerator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeIdGenerator extends AbstractIdGenerator<AnimeId> {

    @Override
    protected AnimeId wrap(String raw) {
        return new AnimeId(raw);
    }

    @Override
    protected int idLength() {
        return AnimeId.ID_LENGTH;
    }

    @Override
    protected char idPrefix() {
        return AnimeId.ID_PREFIX;
    }
}
