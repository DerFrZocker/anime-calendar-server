package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.layer.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.transformer.IntegrationUrlLayer;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformerDataHolder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class LinkLayerAddListener {

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");
    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    @Inject
    AnimeService service;

    public void onAnimeIntegrationLinkCreate(@Observes PostAnimeIntegrationLinkCreateEvent event) {
        LayerTransformerDataHolder<IntegrationUrlLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(
                IntegrationUrlLayer.INSTANCE,
                new IntegrationUrlLayerConfig(event.animeIntegrationLink().integrationId().raw(),
                                              getUrl(event.animeIntegrationLink().integrationId(),
                                                     event.animeIntegrationLink().integrationAnimeId())));
        LayerHolder layerHolder = new LayerHolder(List.of(), layerTransformerDataHolder);

        this.service.updateWithData(event.animeIntegrationLink().animeId(),
                                    AnimeUpdateData.addingLayer(layerHolder),
                                    event.context());
    }

    // TODO 2024-12-27: Refactor, is duplicated
    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s".formatted(integrationAnimeId.raw());
        }
        if (MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }
}
