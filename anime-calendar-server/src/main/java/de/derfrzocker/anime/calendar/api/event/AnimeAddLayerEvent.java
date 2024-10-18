package de.derfrzocker.anime.calendar.api.event;

import de.derfrzocker.anime.calendar.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;

public record AnimeAddLayerEvent(AnimeId animeId, LayerHolder layerHolder) {
}
