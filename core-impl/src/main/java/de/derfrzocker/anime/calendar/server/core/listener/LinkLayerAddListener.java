package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.event.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
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
    @Inject
    IntegrationHelperService integrationHelperService;

    public void onAnimeIntegrationLinkCreate(@Observes PostAnimeIntegrationLinkCreateEvent event) {
        String url = this.integrationHelperService.getUrl(event.animeIntegrationLink().integrationId(),
                                                          event.animeIntegrationLink().integrationAnimeId());
        IntegrationUrlLayerConfig layerConfig = new IntegrationUrlLayerConfig(IntegrationUrlLayerTransformer.LAYER_KEY,
                                                                              event.animeIntegrationLink()
                                                                                   .integrationId(),
                                                                              url);
        LayerStepConfig config = new LayerStepConfig(List.of(), layerConfig);

        this.service.updateWithData(event.animeIntegrationLink().animeId(),
                                    AnimeUpdateData.addingLayer(config),
                                    event.context());
    }
}
