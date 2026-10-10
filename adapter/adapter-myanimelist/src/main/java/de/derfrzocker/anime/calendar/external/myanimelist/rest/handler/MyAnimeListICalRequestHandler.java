package de.derfrzocker.anime.calendar.external.myanimelist.rest.handler;

import de.derfrzocker.anime.calendar.anime.EpisodeBuildOptionBuilder;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.external.ExternalTypes;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import de.derfrzocker.anime.calendar.external.in.ForGeneratingExternalICal;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.model.EpisodeBuildOptionQueryParam;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class MyAnimeListICalRequestHandler {

    private static final ExternalType EXTERNAL_TYPE = ExternalTypes.MYANIMELIST;

    private final ForGeneratingExternalICal iCalService;

    @Inject
    MyAnimeListICalRequestHandler(ForGeneratingExternalICal iCalService) {
        this.iCalService = iCalService;
    }

    public Uni<String> generateICal(ExternalUserId userId, EpisodeBuildOptionQueryParam options) {

        EpisodeBuildOptionBuilder builder = iCalService.optionBuilder();

        applyOptions(builder, options);

        return iCalService.generateICal(EXTERNAL_TYPE, userId, builder.build());
    }

    public Uni<String> generateICal(Set<ExternalAnimeId> animeIds, EpisodeBuildOptionQueryParam options) {

        EpisodeBuildOptionBuilder builder = iCalService.optionBuilder();

        applyOptions(builder, options);

        return iCalService.generateICal(EXTERNAL_TYPE, animeIds, builder.build());
    }

    private void applyOptions(EpisodeBuildOptionBuilder buildOptionBuilder, EpisodeBuildOptionQueryParam options) {
        if (options.region() != null) {
            buildOptionBuilder.region(options.region());
        }

        if (options.useRegionName() != null) {
            buildOptionBuilder.useRegionName(options.useRegionName());
        }

        if (options.languagePriorities() != null) {
            buildOptionBuilder.languagePriorities(options.languagePriorities());
        }

        if (options.streamTypes() != null) {
            buildOptionBuilder.streamTypes(options.streamTypes());
        }
    }
}
