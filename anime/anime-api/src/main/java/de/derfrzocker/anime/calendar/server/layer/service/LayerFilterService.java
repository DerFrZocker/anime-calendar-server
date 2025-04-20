package de.derfrzocker.anime.calendar.server.layer.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerFilter;
import java.util.Optional;

public interface LayerFilterService {

    Optional<LayerFilter<?>> getByKey(LayerKey key, RequestContext context);
}
