package de.derfrzocker.anime.calendar.layer.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.layer.api.LayerTransformer;
import java.util.Optional;

public interface LayerTransformerService {

    Optional<LayerTransformer<?>> getByKey(LayerKey key, RequestContext context);
}
