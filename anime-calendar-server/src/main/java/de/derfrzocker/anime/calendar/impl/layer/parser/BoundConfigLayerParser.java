package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.BoundFilterConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class BoundConfigLayerParser extends AbstractLayerConfigParser<BoundFilterConfig> {

    public static final BoundConfigLayerParser INSTANCE = new BoundConfigLayerParser();

    private BoundConfigLayerParser() {
    }

    @Override
    public @NotNull BoundFilterConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive", -1);
        int maxInclusive = decodeInt(values, "max-inclusive", -1);

        return new BoundFilterConfig(minInclusive, maxInclusive);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull BoundFilterConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("min-inclusive", layerConfig.minInclusive());
        result.put("max-inclusive", layerConfig.maxInclusive());

        return result;
    }
}
