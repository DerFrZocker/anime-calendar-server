package de.derfrzocker.anime.calendar.anime;

import javax.swing.plaf.synth.Region;
import java.util.List;
import java.util.Objects;

public record EpisodeBuildOption(
        Region region, boolean useRegionName, List<String> languagePriorities, List<StreamType> streamTypes) {

    public EpisodeBuildOption {
        Objects.requireNonNull(region);
        Objects.requireNonNull(languagePriorities);
        Objects.requireNonNull(streamTypes);
    }
}
