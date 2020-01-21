package model.programstate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationDictionary<K, V> implements IApplicationDictionary<K, V>
{
    protected Map<K, V> map;

    public ApplicationDictionary()
    {
        map = new ConcurrentHashMap<>(2);
    }

    private ApplicationDictionary(Map<K, V> map)
    {
        this.map = new ConcurrentHashMap<>(map);
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
    public void remove(K key)
    {
        map.remove(key);
    }

    @Override
    public Map<K, V> getMap()
    {
        return map;
    }

    @Override
    public IApplicationDictionary<K, V> shallowCopy()
    {
        return new ApplicationDictionary<>(this.map);
    }

    @Override
    public String toString()
    {
        if (map.isEmpty())
        {
            return "\u2205";
        }
        StringJoiner joiner = new StringJoiner("\n");
        map.forEach((key, value) -> joiner.add(key.toString() + " --> " + value.toString()));
        return joiner.toString();
    }
}
