package com.klaus.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by KlausZ on 2020/8/7.
 */
public class BiHashMap<K, V> extends HashMap<K, V> implements BiMap<K, V> {

    private Map<V, K> inversed;

    public BiHashMap() {
        super();
        this.inversed = new HashMap<>();
    }

    public BiHashMap(int initialCapacity) {
        super(initialCapacity);
        this.inversed = new HashMap<>(initialCapacity);
    }

    public BiHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.inversed = new HashMap<>(initialCapacity, loadFactor);
    }

    public BiHashMap(Map<? extends K, ? extends V> m) {
        this();
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public V put(K key, V value) {
        if (inversed.containsKey(value)) {
            throw new IllegalArgumentException("value already persent : " + value);
        }
        V old = super.put(key, value);
        if (old != null) {
            inversed.remove(old);
        }
        inversed.put(value, key);
        return old;
    }

    @Override
    public V remove(Object key) {
        V v = super.remove(key);
        if (v != null) {
            inversed.remove(v);
        }
        return v;
    }

    @Override
    public K getKey(V v) {
        return inversed.get(v);
    }

    @Override
    public Set<V> values() {
        return inversed.keySet();
    }

}
