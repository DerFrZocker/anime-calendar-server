package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.BoundLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class BoundConfigLayerParser extends AbstractLayerConfigParser<BoundLayerConfig> {

    public static final BoundConfigLayerParser INSTANCE = new BoundConfigLayerParser();

    private BoundConfigLayerParser() {
    }

    @Override
    public @NotNull BoundLayerConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");

        return new BoundLayerConfig(minInclusive, maxInclusive);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull BoundLayerConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("min-inclusive", layerConfig.getMinInclusive());
        result.put("max-inclusive", layerConfig.getMaxInclusive());

        return result;
    }
}
