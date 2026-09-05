package de.derfrzocker.anime.calendar.layer.api;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;

public interface LayerConfig {

    LayerParserKey parserKey();

    LayerKey key();
}
