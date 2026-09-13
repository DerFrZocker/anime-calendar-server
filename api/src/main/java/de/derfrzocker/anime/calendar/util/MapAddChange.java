package de.derfrzocker.anime.calendar.util;

import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

record MapAddChange<K, V extends @Nullable Object>(K key, V value) implements Change<Map<K, V>> {

    @Override
    public Map<K, V> apply(Map<K, V> current) {
        Map<K, V> newMap = new LinkedHashMap<>(current);

        newMap.put(key(), value());

        return newMap;
    }
}
