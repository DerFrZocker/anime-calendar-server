package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.external.ExternalType;
import java.util.List;
import java.util.Optional;

public interface EpisodeBuildOption {

    Region region();

    boolean useRegionName();

    Optional<ExternalType> animeListType();

    List<String> languagePriorities();

    List<StreamType> streamTypes();
}
