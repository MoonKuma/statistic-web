package com.sincetimes.statisticweb.service.translate;

import java.util.Map;

public interface TranslateService<K,V> {

    // translator allows get value for certain key
    V getValue(K key);

    // translator allows get value map
    Map<K, V> getValueMap();
}
