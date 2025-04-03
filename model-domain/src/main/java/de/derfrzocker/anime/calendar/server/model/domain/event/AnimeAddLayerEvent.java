package de.derfrzocker.anime.calendar.server.model.domain.event;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerHolder;

public record AnimeAddLayerEvent(AnimeId animeId, LayerHolder layerHolder) {
}
