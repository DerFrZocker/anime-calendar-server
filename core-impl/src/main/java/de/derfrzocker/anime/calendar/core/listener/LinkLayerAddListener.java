package de.derfrzocker.anime.calendar.core.listener;

import de.derfrzocker.anime.calendar.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.integration.event.PostAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.integration.service.IntegrationHelperService;
import de.derfrzocker.anime.calendar.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.layer.common.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.layer.common.transformer.IntegrationUrlLayerTransformer;
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
