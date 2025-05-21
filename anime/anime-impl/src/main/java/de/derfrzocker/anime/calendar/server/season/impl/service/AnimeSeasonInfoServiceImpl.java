package de.derfrzocker.anime.calendar.server.season.impl.service;

import static de.derfrzocker.anime.calendar.server.season.exception.AnimeSeasonInfoExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.season.exception.AnimeSeasonInfoExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.season.exception.AnimeSeasonInfoExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.season.api.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.server.season.dao.AnimeSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.season.service.AnimeSeasonInfoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class AnimeSeasonInfoServiceImpl implements AnimeSeasonInfoService {

    @Inject
    AnimeSeasonInfoDao dao;
    @Inject
    AnimeSeasonInfoEventPublisher eventPublisher;

    @Override
    public Stream<AnimeSeasonInfo> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<AnimeSeasonInfo> getById(IntegrationId integrationId,
                                             IntegrationAnimeId integrationAnimeId,
                                             int year,
                                             Season season,
                                             RequestContext context) {
        return this.dao.getById(integrationId, integrationAnimeId, year, season, context);
    }

    @Override
    public AnimeSeasonInfo createWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          int year,
                                          Season season,
                                          AnimeSeasonInfoCreateData createData,
                                          RequestContext context) {
        Optional<AnimeSeasonInfo> optional = getById(integrationId, integrationAnimeId, year, season, context);
        if (optional.isPresent()) {
            throw alreadyCreated(integrationId, integrationAnimeId, year, season).get();
        }

        AnimeSeasonInfo info = AnimeSeasonInfo.from(integrationId,
                                                    integrationAnimeId,
                                                    year,
                                                    season,
                                                    createData,
                                                    context);

        this.eventPublisher.firePreCreate(info, createData, context);
        this.dao.create(info, context);
        this.eventPublisher.firePostCreate(info, createData, context);

        return getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(inconsistentNotFound(
                integrationId,
                integrationAnimeId,
                year,
                season));
    }

    @Override
    public AnimeSeasonInfo updateWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          int year,
                                          Season season,
                                          AnimeSeasonInfoUpdateData updateData,
                                          RequestContext context) {
        AnimeSeasonInfo current = getById(integrationId,
                                          integrationAnimeId,
                                          year,
                                          season,
                                          context).orElseThrow(notFound(integrationId,
                                                                        integrationAnimeId,
                                                                        year,
                                                                        season));
        AnimeSeasonInfo updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(inconsistentNotFound(
                integrationId,
                integrationAnimeId,
                year,
                season));
    }

    @Override
    public void deleteById(IntegrationId integrationId,
                           IntegrationAnimeId integrationAnimeId,
                           int year,
                           Season season,
                           RequestContext context) {
        AnimeSeasonInfo info = getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(notFound(
                integrationId,
                integrationAnimeId,
                year,
                season));

        this.eventPublisher.firePreDelete(info, context);
        this.dao.delete(info, context);
        this.eventPublisher.firePostDelete(info, context);
    }
}
