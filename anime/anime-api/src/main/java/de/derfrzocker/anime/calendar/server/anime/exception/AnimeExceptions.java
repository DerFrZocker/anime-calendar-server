package de.derfrzocker.anime.calendar.server.anime.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import java.util.function.Supplier;

public final class AnimeExceptions {

    private static final String RESOURCE_NAME = "Anime";

    public static Supplier<ResourceNotFoundException> notFound(AnimeId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, AnimeId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(AnimeId id) {
        return InconsistentDataException.with(unwrapSafe(id, AnimeId::raw), RESOURCE_NAME);
    }
}
