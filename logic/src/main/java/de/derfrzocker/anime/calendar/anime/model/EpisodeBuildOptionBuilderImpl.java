package de.derfrzocker.anime.calendar.anime.model;

import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOptionBuilder;
import de.derfrzocker.anime.calendar.anime.Region;
import de.derfrzocker.anime.calendar.anime.StreamType;
import de.derfrzocker.anime.calendar.external.ExternalType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class EpisodeBuildOptionBuilderImpl implements EpisodeBuildOptionBuilder {

    public static EpisodeBuildOptionBuilder builder() {
        return new EpisodeBuildOptionBuilderImpl();
    }

    private EpisodeBuildOptionBuilderImpl() {

    }

    private Region region = Region.DE_DE;
    private boolean useRegionName = true;
    @Nullable
    private ExternalType animeListType;
    private List<String> languagePriorities = List.of();
    private List<StreamType> streamTypes = List.of();

    @Override
    public EpisodeBuildOptionBuilder region(Region region) {
        this.region = Objects.requireNonNull(region);
        return this;
    }

    @Override
    public EpisodeBuildOptionBuilder useRegionName(boolean useRegionName) {
        this.useRegionName = useRegionName;
        return null;
    }

    @Override
    public EpisodeBuildOptionBuilder animeListType(ExternalType animeListType) {
        this.animeListType = Objects.requireNonNull(animeListType);
        return this;
    }

    @Override
    public EpisodeBuildOptionBuilder languagePriorities(List<String> languagePriorities) {
        this.languagePriorities = List.copyOf(Objects.requireNonNull(languagePriorities));
        return this;
    }

    @Override
    public EpisodeBuildOptionBuilder streamTypes(List<StreamType> streamTypes) {
        this.streamTypes = List.copyOf(Objects.requireNonNull(streamTypes));
        return this;
    }

    @Override
    public EpisodeBuildOption build() {
        return new EpisodeBuildOptionImpl(region, useRegionName, animeListType, languagePriorities, streamTypes);
    }

    private record EpisodeBuildOptionImpl(
            Region region,
            boolean useRegionName,
            ExternalType _animeListType,
            List<String> languagePriorities,
            List<StreamType> streamTypes) implements EpisodeBuildOption {

        public EpisodeBuildOptionImpl {
            Objects.requireNonNull(region);
            Objects.requireNonNull(languagePriorities);
            Objects.requireNonNull(streamTypes);
        }

        @Override
        public Optional<ExternalType> animeListType() {
            return Optional.ofNullable(_animeListType);
        }
    }
}
