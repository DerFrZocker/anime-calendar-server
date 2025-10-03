package de.derfrzocker.anime.calendar.server.integration.logic.languagename;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.LocalizedNameLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.LocalizedNameLayerTransformer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class AnimeLanguageNameJob {

    private static final NameType OFFICIAL = new NameType("official");
    private static final NameType SYN = new NameType("syn");
    private static final NameLanguage GERMAN = new NameLanguage("de");
    private static final NameLanguage ENGLISH = new NameLanguage("en");

    @Inject
    AnimeService animeService;
    @Inject
    AnimeIntegrationLinkService linkService;

    public void execute(AnimeNameHolder animeNameHolder, RequestContext context) {
        this.linkService.getAllWithId(animeNameHolder.integrationId(), animeNameHolder.integrationAnimeId(), context)
                        .map(AnimeIntegrationLink::animeId)
                        .map(animeId -> this.animeService.getById(animeId, context))
                        .flatMap(Optional::stream)
                        .forEach(anime -> updateAnime(animeNameHolder, anime, context));
    }

    private void updateAnime(AnimeNameHolder animeNameHolder, Anime anime, RequestContext context) {
        updateAnime(animeNameHolder, anime, GERMAN, context);
        updateAnime(animeNameHolder, anime, ENGLISH, context);
    }

    private void updateAnime(AnimeNameHolder animeNameHolder,
                             Anime anime,
                             NameLanguage language,
                             RequestContext context) {
        List<String> names = collectNames(animeNameHolder, language);

        if (names.isEmpty()) {
            return;
        }

        for (LayerStepConfig stepConfig : anime.episodeLayers().reversed()) {
            if (!(stepConfig.transformConfig() instanceof LocalizedNameLayerConfig config)) {
                continue;
            }

            if (!Objects.equals(config.language(), language.raw())) {
                continue;
            }

            if (names.contains(config.name())) {
                return;
            }

            break;
        }

        String newName = names.getFirst();
        LayerStepConfig newStepConfig = new LayerStepConfig(List.of(),
                                                            new LocalizedNameLayerConfig(
                                                                    LocalizedNameLayerTransformer.LAYER_KEY,
                                                                    language.raw(),
                                                                    newName));
        this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(newStepConfig), context);
    }

    private List<String> collectNames(AnimeNameHolder animeNameHolder, NameLanguage language) {
        List<AnimeName> names = animeNameHolder.names()
                                               .stream()
                                               .filter(name -> language.equals(name.language()))
                                               .toList();

        List<AnimeName> possibleNames = names.stream().filter(name -> OFFICIAL.equals(name.type())).toList();
        if (possibleNames.isEmpty()) {
            possibleNames = names.stream().filter(name -> SYN.equals(name.type())).toList();
        }

        return possibleNames.stream().map(AnimeName::name).toList();
    }
}
