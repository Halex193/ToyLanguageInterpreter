package model.programstate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ApplicationDictionary<K, V> implements IApplicationDictionary<K, V>
{
    private Map<K, V> map;

    public ApplicationDictionary()
    {
        map = new HashMap<>(2);
    }

    @Override
    public V lookup(K key)
    {
        return map.get(key);
    }

    @Override
    public void update(K key, V value)
    {
        map.put(key, value);
    }

    @Override
    public String toString()
    {
        StringJoiner joiner = new StringJoiner("|", "{", "}");
        map.forEach((key, value) -> joiner.add(key.toString() + " -> " + value.toString()));
        return joiner.toString();
    }
}
