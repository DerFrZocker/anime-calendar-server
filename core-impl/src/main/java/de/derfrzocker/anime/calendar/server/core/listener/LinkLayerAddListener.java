package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.IntegrationUrlLayerTransformer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class LinkLayerAddListener {

    @Inject
    AnimeService service;

    public void onAnimeIntegrationLinkCreate(@Observes PostAnimeIntegrationLinkCreateEvent event) {
        IntegrationUrlLayerConfig layerConfig = new IntegrationUrlLayerConfig(IntegrationUrlLayerTransformer.LAYER_KEY,
                                                                              event.animeIntegrationLink()
                                                                                   .integrationId(),
                                                                              getUrl(event.animeIntegrationLink()
                                                                                          .integrationId(),
                                                                                     event.animeIntegrationLink()
                                                                                          .integrationAnimeId()));
        LayerStepConfig config = new LayerStepConfig(List.of(), layerConfig);

        this.service.updateWithData(event.animeIntegrationLink().animeId(),
                                    AnimeUpdateData.addingLayer(config),
                                    event.context());
    }

    // TODO 2024-12-27: Refactor, is duplicated
    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (IntegrationIds.SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.CRUNCHYROLL.equals(integrationId)) {
            return "https://www.crunchyroll.com/series/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }
}
