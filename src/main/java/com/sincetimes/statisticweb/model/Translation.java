package com.sincetimes.statisticweb.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/4/19
 */

@Data
public class Translation<K,V> {
    private HashMap<K,V> translationTable = new HashMap<K,V>();
    private HashSet<K> translationSet = new HashSet<K>();
    private V defaultValue;

    // cloned in
    public void initTranslation(@NotNull HashMap<K,V> setMap, V defaultValue){
        this.setDefaultValue(defaultValue);
        for(K key: setMap.keySet()){
            translationTable.put(key, setMap.get(key));
            translationSet.add(key);
        }

    }

    // return default if not found
    public V translate(K key){
        if (translationTable.containsKey(key)){
            return translationTable.get(key);
        }else{
            return defaultValue;
        }
    }

    // return (V)key if not found, this may cause unsafe cast
    public V translateStay(K key){
        if (translationTable.containsKey(key)){
            return translationTable.get(key);
        }else{
            return (V)key;
        }
    }

    public String toString(){
        return "[Report] Your Translation now have a translationTable with length: " +
                translationTable.keySet().size();
    }

}
