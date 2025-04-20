package de.derfrzocker.anime.calendar.server.layer.api;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;

public interface LayerConfig {

    LayerParserKey parserKey();

    LayerKey key();
}
