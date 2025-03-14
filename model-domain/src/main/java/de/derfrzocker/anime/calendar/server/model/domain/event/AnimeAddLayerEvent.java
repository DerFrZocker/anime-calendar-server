package de.derfrzocker.anime.calendar.server.model.domain.event;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;

public record AnimeAddLayerEvent(AnimeId animeId, LayerHolder layerHolder) {
}
