package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import java.util.Map;

public interface LayerConfigParser<T extends LayerConfig> {

    T decode(Map<String, Object> values);

    Map<String, Object> encode(T config);
}
