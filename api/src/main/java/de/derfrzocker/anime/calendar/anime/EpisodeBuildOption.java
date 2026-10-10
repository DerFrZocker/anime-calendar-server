package de.derfrzocker.anime.calendar.anime;

import java.util.List;

public interface EpisodeBuildOption {

    Region region();

    boolean useRegionName();

    List<String> languagePriorities();

    List<StreamType> streamTypes();
}
