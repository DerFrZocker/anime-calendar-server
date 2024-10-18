package de.derfrzocker.anime.calendar.api.event;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.layer.LayerHolder;

public record AnimeAddLayerEvent(AnimeId animeId, LayerHolder layerHolder) {
}
