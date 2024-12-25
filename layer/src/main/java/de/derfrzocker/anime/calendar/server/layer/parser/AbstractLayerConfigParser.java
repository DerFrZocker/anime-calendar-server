package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfigParser;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractLayerConfigParser<T extends LayerConfig> implements LayerConfigParser<T> {

    protected int decodeInt(Map<String, Object> values, String key, int defaultValue) {
        Object value = values.get(key);

        if (value == null) {
            return defaultValue;
        }

        if (!(value instanceof Number number)) {
            throw new IllegalArgumentException("Expected Element of type Number for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return number.intValue();
    }

    protected int decodeInt(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof Number number)) {
            throw new IllegalArgumentException("Expected Element of type Number for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return number.intValue();
    }


    protected String decodeString(Map<String, Object> values, String key, String defaultValue) {
        Object value = values.get(key);

        if (value == null) {
            return defaultValue;
        }

        if (!(value instanceof String string)) {
            throw new IllegalArgumentException("Expected Element of type String for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return string;
    }

    protected String decodeString(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof String string)) {
            throw new IllegalArgumentException("Expected Element of type String for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return string;
    }

    protected <C> Collection<C> decodeCollection(Map<String, Object> values, String key, Collection<C> defaultValue) {
        Object value = values.get(key);

        if (value == null) {
            return defaultValue;
        }

        if (!(value instanceof Collection<?> collection)) {
            throw new IllegalArgumentException("Expected Element of type Collection for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return (Collection<C>) collection;
    }

    protected <C> Collection<C> decodeCollection(Map<String, Object> values, String key) {
        Object value = values.get(key);

        if (!(value instanceof Collection<?> collection)) {
            throw new IllegalArgumentException("Expected Element of type Collection for key " + key + " but got " + (value == null ? "null" : value.getClass()));
        }

        return (Collection<C>) collection;
    }
}
