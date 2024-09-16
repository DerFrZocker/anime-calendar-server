/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionFilterConfig;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class RegionFilterLayerConfigParser extends AbstractLayerConfigParser<RegionFilterConfig> {

    private final static String APPLICABLE_REGIONS = "applicable-regions";
    public static final RegionFilterLayerConfigParser INSTANCE = new RegionFilterLayerConfigParser();

    private RegionFilterLayerConfigParser() {
    }

    @Override
    public @NotNull RegionFilterConfig decode(@NotNull Map<String, Object> values) {
        return new RegionFilterConfig(decodeRegions(values, APPLICABLE_REGIONS));
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionFilterConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put(APPLICABLE_REGIONS, encode(layerConfig.applicableRegions()));

        return result;
    }

    private Set<Region> decodeRegions(Map<String, Object> values, String key) {
        Set<Region> result = EnumSet.noneOf(Region.class);
        if (values.containsKey(key)) {
            Collection<String> regions = decodeCollection(values, key);
            regions.stream().map(Region::valueOf).forEach(result::add);
        }

        return result;
    }

    private Object encode(Collection<Region> values) {
        return values.stream().map(Enum::toString).toList();
    }
}
