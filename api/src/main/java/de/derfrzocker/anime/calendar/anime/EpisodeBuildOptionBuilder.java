package de.derfrzocker.anime.calendar.anime;

import java.util.List;

public interface EpisodeBuildOptionBuilder {

    EpisodeBuildOptionBuilder region(Region region);

    EpisodeBuildOptionBuilder useRegionName(boolean useRegionName);

    EpisodeBuildOptionBuilder languagePriorities(List<String> languagePriorities);

    EpisodeBuildOptionBuilder streamTypes(List<StreamType> streamTypes);

    EpisodeBuildOption build();
}
