package com.klaus.map;

import java.util.Map;
import java.util.Set;

/**
 * Created by KlausZ on 2020/8/7.
 */
public interface BiMap<K, V> extends Map<K, V> {

    default V getVal(K k) {
        return get(k);
    }

    K getKey(V v);

    Set<V> values();

}
