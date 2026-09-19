package de.derfrzocker.anime.calendar.anime;

public interface LayerFilter<T extends LayerConfigData> {

    boolean shouldSkip(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, T config);
}
