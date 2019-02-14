package sample;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexedLinkedHashMap<K,V> extends LinkedHashMap {

    public IndexedLinkedHashMap() { }

    private HashMap<K, Integer> index=new HashMap<>();
    private HashMap<Integer, K> material=new HashMap<>();
    Integer curr = 0;


    public void putNew(K key,V val){
        super.put(key,val);
        index.put(key, curr);
        material.put(curr, key);
        curr++;
    }

    public Integer getIndex(K i){
        return index.get(i);
    }

    public K getValue(Integer i){
        return material.get(i);
    }

}
