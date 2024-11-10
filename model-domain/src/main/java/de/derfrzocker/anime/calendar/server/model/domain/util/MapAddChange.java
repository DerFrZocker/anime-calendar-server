package de.derfrzocker.anime.calendar.server.model.domain.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapAddChange<K, V, T extends Map<K, V>> implements Change<T> {

    private final K key;
    private final V value;

    public MapAddChange(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T apply(T current) {
        Map<K, V> newMap = new LinkedHashMap<>(current);

        newMap.put(key, value);

        return (T) newMap;
    }
}
