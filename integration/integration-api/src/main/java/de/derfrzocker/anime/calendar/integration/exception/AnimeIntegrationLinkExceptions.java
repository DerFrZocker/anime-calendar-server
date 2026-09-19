package de.derfrzocker.anime.calendar.integration.exception;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

import java.util.function.Supplier;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;

public final class AnimeIntegrationLinkExceptions {

    private static final String RESOURCE_NAME = "AnimIntegrationLink";
    private static final String ALREADY_CREATED = "%s with anime id '%s', integration id '%s' and integration anime id '%s' was already created and cannot be created again.";

    private AnimeIntegrationLinkExceptions() {
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
