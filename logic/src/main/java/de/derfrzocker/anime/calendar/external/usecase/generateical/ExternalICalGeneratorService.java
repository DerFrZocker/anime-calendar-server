package de.derfrzocker.anime.calendar.external.usecase.generateical;

import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOptionBuilder;
import de.derfrzocker.anime.calendar.anime.model.EpisodeBuildOptionBuilderImpl;
import de.derfrzocker.anime.calendar.anime.out.ForManagingAnime;
import de.derfrzocker.anime.calendar.anime.service.EpisodeBuildService;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeLink;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import de.derfrzocker.anime.calendar.external.in.ForGeneratingExternalICal;
import de.derfrzocker.anime.calendar.external.out.ForFetchingExternalUserInfo;
import de.derfrzocker.anime.calendar.external.out.ForManagingExternalAnimeLink;
import de.derfrzocker.anime.calendar.ical.renderer.AnimeICalRenderer;
import de.derfrzocker.anime.calendar.ical.service.AnimeICalRendererService;
import io.smallrye.common.annotation.Identifier;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.Collection;

@ApplicationScoped
class ExternalICalGeneratorService implements ForGeneratingExternalICal {

    private final Instance<ForFetchingExternalUserInfo> externalUserInfoInstance;
    private final ForManagingExternalAnimeLink externalAnimeLinkService;
    private final ForManagingAnime animeService;
    private final EpisodeBuildService episodeBuildService;
    private final AnimeICalRendererService animeICalRendererService;

    @Inject
    ExternalICalGeneratorService(
            Instance<ForFetchingExternalUserInfo> externalUserInfoInstance,
            ForManagingExternalAnimeLink externalAnimeLinkService,
            ForManagingAnime animeService,
            EpisodeBuildService episodeBuildService,
            AnimeICalRendererService animeICalRendererService) {

        this.externalUserInfoInstance = externalUserInfoInstance;
        this.externalAnimeLinkService = externalAnimeLinkService;
        this.animeService = animeService;
        this.episodeBuildService = episodeBuildService;
        this.animeICalRendererService = animeICalRendererService;
    }

    @Override
    public Uni<String> generateICal(ExternalType type, ExternalUserId userId, EpisodeBuildOption option) {
        return externalUserInfoService(type)
                .fetchWatchingAnime(userId)
                .flatMap(animeIds -> generateICal(type, animeIds, option));
    }

    @Override
    public Uni<String> generateICal(
            ExternalType type,
            Collection<ExternalAnimeId> externalAnimeIds,
            EpisodeBuildOption option) {

        AnimeICalRenderer renderer = animeICalRendererService.createRenderer(option);

        return externalAnimeLinkService
                .retrieveAllWith(Multi.createFrom().iterable(externalAnimeIds))
                .map(ExternalAnimeLink::animeId)
                .stage(animeService::retrieveAll)
                .onItem()
                .transformToIterable(anime -> episodeBuildService.buildEpisodes(anime, option))
                .invoke(renderer::append)
                .onItem()
                .ignoreAsUni()
                .replaceWith(renderer::render);
    }

    @Override
    public EpisodeBuildOptionBuilder optionBuilder() {
        return EpisodeBuildOptionBuilderImpl.builder();
    }

    private ForFetchingExternalUserInfo externalUserInfoService(ExternalType type) {
        return externalUserInfoInstance.select(Identifier.Literal.of(type.raw())).get();
    }
}
