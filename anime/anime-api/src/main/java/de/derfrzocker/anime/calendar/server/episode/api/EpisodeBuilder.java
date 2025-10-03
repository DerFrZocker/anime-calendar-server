package de.derfrzocker.anime.calendar.server.episode.api;

import com.spencerwi.either.Either;
import java.time.Instant;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

public final class EpisodeBuilder {

    private final int episodeIndex;
    @Nullable
    private String episodeName;
    @Nullable
    private String episodeNameLanguage;
    @Nullable
    private Either<String, Integer> episodeNumber;
    @Nullable
    private Either<String, Integer> episodeNumbers;
    @Nullable
    private StreamType type;
    @Nullable
    private String animeListLink;
    @Nullable
    private String streamingLink;
    @Nullable
    private Instant streamingTime;
    private int episodeLength;
    @Nullable
    private String integrationLink;

    private EpisodeBuilder(int episodeIndex) {
        this.episodeIndex = episodeIndex;
    }

    public static EpisodeBuilder anEpisode(int episodeIndex) {
        return new EpisodeBuilder(episodeIndex);
    }

    public EpisodeBuilder withEpisodeName(String episodeName) {
        this.episodeName = episodeName;
        return this;
    }

    public EpisodeBuilder withEpisodeNameLanguage(String episodeNameLanguage) {
        this.episodeNameLanguage = episodeNameLanguage;
        return this;
    }

    public String episodeNameLanguage() {
        return this.episodeNameLanguage;
    }

    public EpisodeBuilder withEpisodeNumber(Either<String, Integer> episodeNumber) {
        this.episodeNumber = episodeNumber;
        return this;
    }

    public EpisodeBuilder withEpisodeNumbers(Either<String, Integer> episodeNumbers) {
        this.episodeNumbers = episodeNumbers;
        return this;
    }

    public EpisodeBuilder withType(StreamType type) {
        this.type = type;
        return this;
    }

    public StreamType type() {
        return this.type;
    }

    public EpisodeBuilder withAnimeListLink(String animeListLink) {
        this.animeListLink = animeListLink;
        return this;
    }

    public EpisodeBuilder withStreamingLink(String streamingLink) {
        this.streamingLink = streamingLink;
        return this;
    }

    public EpisodeBuilder withStreamingTime(Instant streamingTime) {
        this.streamingTime = streamingTime;
        return this;
    }

    public EpisodeBuilder withEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
        return this;
    }

    public EpisodeBuilder withIntegrationLink(String integrationLink) {
        this.integrationLink = integrationLink;
        return this;
    }

    public int episodeIndex() {
        return this.episodeIndex;
    }

    public Episode build() {
        return new Episode(episodeIndex,
                           episodeName,
                           episodeNumber == null ? null : episodeNumber.fold(Function.identity(), String::valueOf),
                           episodeNumbers == null ? null : episodeNumbers.fold(Function.identity(), String::valueOf),
                           type,
                           animeListLink,
                           streamingLink,
                           streamingTime,
                           episodeLength,
                           integrationLink);
    }
}
