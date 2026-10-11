package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;

public record SimpleOffsetIntegerConfig(int value, int offset) implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "simple_offset_integer";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);
}
