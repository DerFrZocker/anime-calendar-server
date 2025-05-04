package de.derfrzocker.anime.calendar.server.integration.myanimelist.impl.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoService;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.api.MyAnimeListSeasonInfo;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.servce.MyAnimeListSeasonInfoService;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MyAnimeListNameAndSeasonUpdateRequestHandler {

    private static final NameType DEFAULT_NAME_TYPE = new NameType("main");
    private static final NameLanguage DEFAULT_NAME_LANGUAGE = new NameLanguage("x-jat");

    private static final Logger LOG = Logger.getLogger(MyAnimeListNameAndSeasonUpdateRequestHandler.class);

    @Inject
    MyAnimeListSeasonInfoService seasonInfoService;
    @Inject
    AnimeNameHolderService nameService;
    @Inject
    AnimeSeasonInfoService seasonService;

    public Uni<Void> createOrUpdate(RequestContext context) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Year year = Year.of(now.getYear());
        Season season = Season.getSeason(now.getMonth());


        List<MyAnimeListSeasonInfo> data = new ArrayList<>();
        data.addAll(this.seasonInfoService.getAllByYearAndSeason(year, season, context));
        data.addAll(this.seasonInfoService.getAllByYearAndSeason(season.nextYear(year), season.nextSeason(), context));
        data.addAll(this.seasonInfoService.getAllByYearAndSeason(season.previousYear(year),
                                                                 season.previousSeason(),
                                                                 context));

        LOG.info("Creating or updating '%d' anime names and season.".formatted(data.size()));

        return Multi.createFrom()
                    .iterable(data)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .invoke(info -> createOrUpdateName(info, context))
                    .invoke(info -> createOrUpdateSeason(info, context))
                    .collect()
                    .asList()
                    .onFailure()
                    .invoke(d -> {
                        LOG.error("MyAnimeList names and season creating or updating failed.", d);
                    })
                    .invoke(() -> {
                        LOG.info("MyAnimeList names and season created or updated successfully.");
                    })
                    .replaceWithVoid();
    }

    private void createOrUpdateName(MyAnimeListSeasonInfo info, RequestContext context) {
        Optional<AnimeNameHolder> current = this.nameService.getById(IntegrationIds.MY_ANIME_LIST,
                                                                     info.animeId(),
                                                                     context);

        if (current.isPresent()) {
            updateName(info, current.get(), context);
        } else {
            createName(info, context);
        }
    }

    private void createOrUpdateSeason(MyAnimeListSeasonInfo info, RequestContext context) {
        Optional<AnimeSeasonInfo> current = this.seasonService.getById(IntegrationIds.MY_ANIME_LIST,
                                                                       info.animeId(),
                                                                       info.year().getValue(),
                                                                       info.season(),
                                                                       context);

        if (current.isPresent()) {
            updateSeason(info, current.get(), context);
        } else {
            createSeason(info, context);
        }
    }

    private void createName(MyAnimeListSeasonInfo info, RequestContext context) {
        this.nameService.createWithData(IntegrationIds.MY_ANIME_LIST,
                                        info.animeId(),
                                        new AnimeNameHolderCreateData(List.of(info.name())),
                                        context);
    }

    private void updateName(MyAnimeListSeasonInfo info, AnimeNameHolder current, RequestContext context) {
        AnimeName main = null;
        for (AnimeName name : current.names()) {
            if (!DEFAULT_NAME_TYPE.equals(name.type())) {
                continue;
            }
            if (!DEFAULT_NAME_LANGUAGE.equals(name.language())) {
                continue;
            }

            main = name;
            break;
        }

        if (main != null) {
            if (Objects.equals(info.name().name(), main.name())) {
                return;
            }

            AnimeName finalMain = main;
            AnimeNameHolderUpdateData updateData = new AnimeNameHolderUpdateData(current1 -> {
                List<AnimeName> newList = new ArrayList<>(current1);

                newList.remove(finalMain);
                newList.add(info.name());

                return List.of();
            });

            this.nameService.updateWithData(IntegrationIds.MY_ANIME_LIST, info.animeId(), updateData, context);
            return;
        }

        this.nameService.updateWithData(IntegrationIds.MY_ANIME_LIST,
                                        info.animeId(),
                                        new AnimeNameHolderUpdateData(Change.addingToList(info.name())),
                                        context);
    }

    private void createSeason(MyAnimeListSeasonInfo info, RequestContext context) {
        this.seasonService.createWithData(IntegrationIds.MY_ANIME_LIST,
                                          info.animeId(),
                                          info.year().getValue(),
                                          info.season(),
                                          new AnimeSeasonInfoCreateData(),
                                          context);
    }

    private void updateSeason(MyAnimeListSeasonInfo info, AnimeSeasonInfo current, RequestContext context) {
        // Since it currently only has keys, we don't need to do anything for updating
    }
}
