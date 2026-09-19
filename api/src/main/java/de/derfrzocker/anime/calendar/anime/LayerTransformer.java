package de.derfrzocker.anime.calendar.anime;

public interface LayerTransformer<T extends LayerConfigData> {

    void transform(Anime anime, EpisodeBuildOption option, EpisodeBuilder builder, T config);
}
