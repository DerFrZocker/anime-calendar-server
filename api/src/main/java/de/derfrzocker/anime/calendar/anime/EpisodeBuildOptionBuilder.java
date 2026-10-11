package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.external.ExternalType;
import java.util.List;

public interface EpisodeBuildOptionBuilder {

    EpisodeBuildOptionBuilder region(Region region);

    EpisodeBuildOptionBuilder useRegionName(boolean useRegionName);

    EpisodeBuildOptionBuilder animeListType(ExternalType animeListType);

    EpisodeBuildOptionBuilder languagePriorities(List<String> languagePriorities);

    EpisodeBuildOptionBuilder streamTypes(List<StreamType> streamTypes);

    EpisodeBuildOption build();
}
