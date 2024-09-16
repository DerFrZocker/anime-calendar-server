package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.api.layer.LayerConfigParser;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractLayerConfigParser<T extends LayerConfig> implements LayerConfigParser<T> {

    protected int decodeInt(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof Number number)) {
            throw new IllegalArgumentException("Expected Element of type Number for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return number.intValue();
    }

    protected String decodeString(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof String string)) {
            throw new IllegalArgumentException("Expected Element of type String for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return string;
    }

    protected <C> Collection<C> decodeCollection(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof Collection<?> collection)) {
            throw new IllegalArgumentException("Expected Element of type Collection for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return (Collection<C>) collection;
    }


}
