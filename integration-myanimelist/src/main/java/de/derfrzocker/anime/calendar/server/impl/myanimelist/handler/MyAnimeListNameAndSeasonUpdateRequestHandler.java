package de.derfrzocker.anime.calendar.server.impl.myanimelist.handler;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoService;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListAnimeListResponse;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListRestClient;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.client.MyAnimeListSeason;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfoCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.season.Season;
import de.derfrzocker.anime.calendar.server.model.domain.util.ListAddChange;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MyAnimeListNameAndSeasonUpdateRequestHandler {

    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final NameType DEFAULT_NAME_TYPE = new NameType("main");
    private static final NameLanguage DEFAULT_NAME_LANGUAGE = new NameLanguage("x-jat");

    private static final Logger LOG = Logger.getLogger(MyAnimeListNameAndSeasonUpdateRequestHandler.class);

    @RestClient
    MyAnimeListRestClient restClient;
    @Inject
    AnimeNameHolderService nameService;
    @Inject
    AnimeSeasonInfoService seasonService;
    @ConfigProperty(name = "myanimelist.name-and-season-update.client.limit")
    int limit;

    public Uni<Void> createOrUpdate(RequestContext context) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        int year = now.getYear();
        Season season = Season.getSeason(now.getMonth());

        List<DataHolder> data = new ArrayList<>();
        data.addAll(read(year,
                         season,
                         this.restClient.getSeasonAnime(year, MyAnimeListSeason.from(season), this.limit)));
        data.addAll(read(season.nextYear(year),
                         season.nextSeason(),
                         this.restClient.getSeasonAnime(season.nextYear(year),
                                                        MyAnimeListSeason.from(season.nextSeason()),
                                                        this.limit)));
        data.addAll(read(season.previousYear(year),
                         season.previousSeason(),
                         this.restClient.getSeasonAnime(season.previousYear(year),
                                                        MyAnimeListSeason.from(season.previousSeason()),
                                                        this.limit)));

        LOG.info("Creating or updating '%d' anime names and season.".formatted(data.size()));

        // TODO 2024-12-17: Read a bit more into context propagation, can this be done better?
        return Multi.createFrom()
                    .iterable(data)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .invoke(read -> createOrUpdateName(read, context))
                    .invoke(read -> createOrUpdateSeason(read, context))
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

    // Need to have this annotation otherwise multi won't work, because of missing active context :|
    @ActivateRequestContext
    void createOrUpdateName(DataHolder read, RequestContext context) {
        Optional<AnimeNameHolder> current = this.nameService.getById(MY_ANIME_LIST, read.id(), context);

        if (current.isPresent()) {
            updateName(read, current.get(), context);
        } else {
            createName(read, context);
        }
    }

    // Need to have this annotation otherwise multi won't work, because of missing active context :|
    @ActivateRequestContext
    void createOrUpdateSeason(DataHolder read, RequestContext context) {
        Optional<AnimeSeasonInfo> current = this.seasonService.getById(MY_ANIME_LIST,
                                                                       read.id(),
                                                                       read.year(),
                                                                       read.season(),
                                                                       context);

        if (current.isPresent()) {
            updateSeason(read, current.get(), context);
        } else {
            createSeason(read, context);
        }
    }

    @ActivateRequestContext
    void createName(DataHolder read, RequestContext context) {
        this.nameService.createWithData(MY_ANIME_LIST,
                                        read.id(),
                                        new AnimeNameHolderCreateData(List.of(read.name())),
                                        context);
    }

    @ActivateRequestContext
    void updateName(DataHolder read, AnimeNameHolder current, RequestContext context) {
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
            if (Objects.equals(read.name().name(), main.name())) {
                return;
            }

            AnimeName finalMain = main;
            AnimeNameHolderUpdateData updateData = new AnimeNameHolderUpdateData(current1 -> {
                List<AnimeName> newList = new ArrayList<>(current1);

                newList.remove(finalMain);
                newList.add(read.name());

                return List.of();
            });

            this.nameService.updateWithData(MY_ANIME_LIST, read.id(), updateData, context);
            return;
        }

        this.nameService.updateWithData(MY_ANIME_LIST,
                                        read.id(),
                                        new AnimeNameHolderUpdateData(new ListAddChange<>(read.name())),
                                        context);
    }

    @ActivateRequestContext
    void createSeason(DataHolder read, RequestContext context) {
        this.seasonService.createWithData(MY_ANIME_LIST,
                                          read.id(),
                                          read.year(),
                                          read.season(),
                                          new AnimeSeasonInfoCreateData(),
                                          context);
    }

    private void updateSeason(DataHolder read, AnimeSeasonInfo current, RequestContext context) {
        // Since it currently only has keys, we don't need to do anything for updating
    }

    private List<DataHolder> read(int year, Season season, MyAnimeListAnimeListResponse response) {
        return response.data()
                       .stream()
                       .map(MyAnimeListAnimeListResponse.Data::node)
                       .map(node -> new DataHolder(year,
                                                   season,
                                                   new IntegrationAnimeId(String.valueOf(node.id())),
                                                   new AnimeName(DEFAULT_NAME_TYPE,
                                                                 DEFAULT_NAME_LANGUAGE,
                                                                 node.title())))
                       .toList();
    }

    private record DataHolder(int year, Season season, IntegrationAnimeId id, AnimeName name) {

    }
}
