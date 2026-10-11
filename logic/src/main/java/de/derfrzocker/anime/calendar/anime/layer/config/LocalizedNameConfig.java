package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import java.util.Objects;

public record LocalizedNameConfig(String language, String name) implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "localized_name";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);

    public LocalizedNameConfig {
        Objects.requireNonNull(language);
        Objects.requireNonNull(name);
    }
}
