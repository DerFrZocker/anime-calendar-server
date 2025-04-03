package de.derfrzocker.anime.calendar.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

record MapAddChange<K, V>(K key, V value) implements Change<Map<K, V>> {

    @Override
    public Map<K, V> apply(Map<K, V> current) {
        Map<K, V> newMap = new LinkedHashMap<>(current);

        newMap.put(key(), value());

        return newMap;
    }
}
