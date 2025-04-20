package de.derfrzocker.anime.calendar.server.layer.service;

import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import java.util.Map;

public interface LayerConfigParserService {

    <T extends LayerConfig> T decode(Map<String, Object> values);

    <T extends LayerConfig> Map<String, Object> encode(T config);
}
