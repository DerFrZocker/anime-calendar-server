package de.derfrzocker.anime.calendar.server.core.impl.season;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoService;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoUpdateData;
import de.derfrzocker.anime.calendar.core.season.Season;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
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
            // TODO 2024-12-17: Better exception
            throw new RuntimeException();
        }

        AnimeSeasonInfo animeSeasonInfo = AnimeSeasonInfo.from(integrationId,
                                                               integrationAnimeId,
                                                               year,
                                                               season,
                                                               createData,
                                                               context);

        this.eventPublisher.firePreCreateEvent(integrationId,
                                               integrationAnimeId,
                                               year,
                                               season,
                                               createData,
                                               animeSeasonInfo,
                                               context);
        this.dao.create(animeSeasonInfo, context);
        this.eventPublisher.firePostCreateEvent(integrationId,
                                                integrationAnimeId,
                                                year,
                                                season,
                                                createData,
                                                animeSeasonInfo,
                                                context);

        return animeSeasonInfo;
    }

    @Override
    public AnimeSeasonInfo updateWithData(IntegrationId integrationId,
                                          IntegrationAnimeId integrationAnimeId,
                                          int year,
                                          Season season,
                                          AnimeSeasonInfoUpdateData updateData,
                                          RequestContext context) {
        AnimeSeasonInfo current = getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(
                ResourceNotFoundException.withSeasonInfo(integrationId, integrationAnimeId, year, season));
        AnimeSeasonInfo updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdateEvent(integrationId,
                                               integrationAnimeId,
                                               year,
                                               season,
                                               updateData,
                                               current,
                                               updated,
                                               context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdateEvent(integrationId,
                                                integrationAnimeId,
                                                year,
                                                season,
                                                updateData,
                                                current,
                                                updated,
                                                context);

        return updated;
    }

    @Override
    public void deleteById(IntegrationId integrationId,
                           IntegrationAnimeId integrationAnimeId,
                           int year,
                           Season season,
                           RequestContext context) {
        AnimeSeasonInfo animeSeasonInfo = getById(integrationId, integrationAnimeId, year, season, context).orElseThrow(
                ResourceNotFoundException.withSeasonInfo(integrationId, integrationAnimeId, year, season));

        this.eventPublisher.firePreDeleteEvent(integrationId,
                                               integrationAnimeId,
                                               year,
                                               season,
                                               animeSeasonInfo,
                                               context);
        this.dao.delete(animeSeasonInfo, context);
        this.eventPublisher.firePostDeleteEvent(integrationId,
                                                integrationAnimeId,
                                                year,
                                                season,
                                                animeSeasonInfo,
                                                context);
    }
}
