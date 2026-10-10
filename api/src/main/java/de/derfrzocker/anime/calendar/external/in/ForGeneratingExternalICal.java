package de.derfrzocker.anime.calendar.external.in;

import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOptionBuilder;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import io.smallrye.mutiny.Uni;
import java.util.Collection;

public interface ForGeneratingExternalICal {

    Uni<String> generateICal(ExternalType type, ExternalUserId userId, EpisodeBuildOption option);

    Uni<String> generateICal(ExternalType type, Collection<ExternalAnimeId> animeIds, EpisodeBuildOption option);

    EpisodeBuildOptionBuilder optionBuilder();
}
