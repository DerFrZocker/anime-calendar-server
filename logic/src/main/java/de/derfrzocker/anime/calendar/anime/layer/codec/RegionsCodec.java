package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.Region;
import de.derfrzocker.anime.calendar.anime.layer.config.RegionsConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.util.List;

@Identifier(RegionsConfig.DATA_TYPE_RAW)
@ApplicationScoped
class RegionsCodec implements LayerConfigCodec<RegionsConfig> {

    private final static String REGIONS_KEY = "regions";

    @Override
    public RegionsConfig deserialize(JsonObject data) {

        List<Region> regions = data
                .getJsonArray(REGIONS_KEY)
                .stream()
                .map(JsonValue::toString)
                .map(Region::valueOf)
                .toList();

        return new RegionsConfig(regions);
    }

    @Override
    public JsonObject serialize(RegionsConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder regions = Json.createArrayBuilder();

        data.regions().forEach(region -> regions.add(region.name()));

        builder.add(REGIONS_KEY, regions);

        return builder.build();
    }
}
