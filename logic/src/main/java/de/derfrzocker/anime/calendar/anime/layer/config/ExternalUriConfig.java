package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.external.ExternalType;
import java.net.URI;
import java.util.Objects;

public record ExternalUriConfig(ExternalType externalType, URI uri) implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "external_uri";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);

    public ExternalUriConfig {
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(uri);
    }
}
