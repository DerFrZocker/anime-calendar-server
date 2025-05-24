package de.derfrzocker.anime.calendar.server.integration.name.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.util.function.Supplier;

public final class AnimeNameHolderExceptions {

    private static final String RESOURCE_NAME = "AnimeNameHolder";
    private static final String NOT_FOUND = "%s with integration id '%s' and integration anime id '%s' not found.";
    private static final String NOT_FOUND_BUT_SHOULD = "%s with integration id '%s' and integration anime id '%s' not found, but it should be present.";
    private static final String ALREADY_CREATED = "%s with integration id '%s' and integration anime id '%s' was already created and cannot be created again.";

    private AnimeNameHolderExceptions() {
    }

    public static Supplier<ResourceNotFoundException> notFound(IntegrationId integrationId,
                                                               IntegrationAnimeId integrationAnimeId) {
        return ResourceNotFoundException.from(NOT_FOUND.formatted(RESOURCE_NAME,
                                                                  unwrapSafe(integrationId, IntegrationId::raw),
                                                                  unwrapSafe(integrationAnimeId,
                                                                             IntegrationAnimeId::raw)));
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(IntegrationId integrationId,
                                                                           IntegrationAnimeId integrationAnimeId) {
        return InconsistentDataException.from(NOT_FOUND_BUT_SHOULD.formatted(RESOURCE_NAME,
                                                                             unwrapSafe(integrationId,
                                                                                        IntegrationId::raw),
                                                                             unwrapSafe(integrationAnimeId,
                                                                                        IntegrationAnimeId::raw)));
    }

    public static Supplier<AlreadyCreatedException> alreadyCreated(IntegrationId integrationId,
                                                                   IntegrationAnimeId integrationAnimeId) {
        return AlreadyCreatedException.from(ALREADY_CREATED.formatted(RESOURCE_NAME,
                                                                      unwrapSafe(integrationId, IntegrationId::raw),
                                                                      unwrapSafe(integrationAnimeId,
                                                                                 IntegrationAnimeId::raw)));
    }
}
