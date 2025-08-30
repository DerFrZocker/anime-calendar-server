package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.exception.AnimeExceptions;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class UpdateAnimeTimeFromSyoboiTask {

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;
    @Inject
    CheckEpisodeCountSubTask episodeCountSubTask;
    @Inject
    CheckOrgStreamTimeSubTask orgStreamTimeSubTask;
    @Inject
    CheckSubStreamTimeSubTask subStreamTimeSubTask;
    @Inject
    CheckEpisodeLengthSubTask episodeLengthSubTask;
    @Inject
    CheckEpisodeIndexSubTask episodeIndexSubTask;

    Uni<Void> executeAsync(AnimeScheduleHolder holder, RequestContext context) {
        return Multi.createFrom()
                    .iterable(holder.links())
                    .onItem()
                    .transformToUniAndMerge(link -> handleSinglePresentLink(link, holder, context))
                    .collect()
                    .asList()
                    .replaceWithVoid();

    }

    private Uni<Void> handleSinglePresentLink(AnimeIntegrationLink link,
                                              AnimeScheduleHolder data,
                                              RequestContext context) {
        return Uni.createFrom()
                  .item(() -> this.animeService.getById(link.animeId(), context)
                                               .orElseThrow(AnimeExceptions.inconsistentNotFound(link.animeId())))
                  .onItem()
                  .transformToUni(anime -> this.episodeCountSubTask.executeAsync(data, anime, context))
                  .onItem()
                  .transformToUni(anime -> handleWithEpisode(anime, link, data, context))
                  .replaceWithVoid();
    }

    private Uni<Anime> handleWithEpisode(Anime baseAnime,
                                         AnimeIntegrationLink link,
                                         AnimeScheduleHolder data,
                                         RequestContext context) {
        Episode episode = getEpisode(baseAnime, data, StreamType.ORG, context);

        return orgStreamTimeSubTask.executeAsync(data, baseAnime, episode, context)
                                   .onItem()
                                   .transformToUni(anime -> subStreamTimeSubTask.executeAsync(data,
                                                                                              baseAnime,
                                                                                              episode,
                                                                                              context))
                                   .onItem()
                                   .transformToUni(anime -> episodeLengthSubTask.executeAsync(data,
                                                                                              baseAnime,
                                                                                              episode,
                                                                                              context))
                                   .onItem()
                                   .transformToUni(anime -> episodeIndexSubTask.executeAsync(data,
                                                                                             baseAnime,
                                                                                             episode,
                                                                                             context));
    }

    private Episode getEpisode(Anime anime, AnimeScheduleHolder data, StreamType streamType, RequestContext context) {
        AnimeOptions animeOptions = AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                       .withUseRegionName(false)
                                                       .withStreamTypes(streamType)
                                                       .build();
        int index = data.schedule().episode() - 1;
        return this.episodeBuilderService.buildEpisode(anime, animeOptions, index, context);
    }
}
