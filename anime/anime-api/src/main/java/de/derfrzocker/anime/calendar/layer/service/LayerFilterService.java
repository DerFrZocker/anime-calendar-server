package de.derfrzocker.anime.calendar.layer.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.layer.api.LayerFilter;
import java.util.Optional;

public interface LayerFilterService {

    Optional<LayerFilter<?>> getByKey(LayerKey key, RequestContext context);
}
