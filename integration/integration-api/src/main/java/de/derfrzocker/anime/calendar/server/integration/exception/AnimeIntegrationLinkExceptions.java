package de.derfrzocker.anime.calendar.server.integration.exception;

import static de.derfrzocker.anime.calendar.server.model.domain.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.util.function.Supplier;

public final class AnimeIntegrationLinkExceptions {

    private static final String RESOURCE_NAME = "AnimIntegrationLink";
    private static final String NOT_FOUND = "%s with anime id '%s', integration id '%s' and integration anime id '%s' not found.";
    private static final String NOT_FOUND_BUT_SHOULD = "%s with anime id '%s', integration id '%s' and integration anime id '%s' not found, but it should be present.";
    private static final String ALREADY_CREATED = "%s with anime id '%s', integration id '%s' and integration anime id '%s' was already created and cannot be created again.";

    private AnimeIntegrationLinkExceptions() {
    }

    public static Supplier<ResourceNotFoundException> notFound(AnimeId animeId,
                                                               IntegrationId integrationId,
                                                               IntegrationAnimeId integrationAnimeId) {
        return ResourceNotFoundException.from(NOT_FOUND.formatted(RESOURCE_NAME,
                                                                  unwrapSafe(animeId, AnimeId::raw),
                                                                  unwrapSafe(integrationId, IntegrationId::raw),
                                                                  unwrapSafe(integrationAnimeId,
                                                                             IntegrationAnimeId::raw)));
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(AnimeId animeId,
                                                                           IntegrationId integrationId,
                                                                           IntegrationAnimeId integrationAnimeId) {
        return InconsistentDataException.from(NOT_FOUND_BUT_SHOULD.formatted(RESOURCE_NAME,
                                                                             unwrapSafe(animeId, AnimeId::raw),
                                                                             unwrapSafe(integrationId,
                                                                                        IntegrationId::raw),
                                                                             unwrapSafe(integrationAnimeId,
                                                                                        IntegrationAnimeId::raw)));
    }

    public static Supplier<AlreadyCreatedException> alreadyCreated(AnimeId animeId,
                                                                   IntegrationId integrationId,
                                                                   IntegrationAnimeId integrationAnimeId) {
        return AlreadyCreatedException.from(ALREADY_CREATED.formatted(RESOURCE_NAME,
                                                                      unwrapSafe(animeId, AnimeId::raw),
                                                                      unwrapSafe(integrationId, IntegrationId::raw),
                                                                      unwrapSafe(integrationAnimeId,
                                                                                 IntegrationAnimeId::raw)));
    }
}
