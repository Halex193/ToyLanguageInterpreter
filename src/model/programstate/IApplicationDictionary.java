package model.programstate;

import java.util.Map;
import java.util.Set;

public interface IApplicationDictionary<K, V>
{
    V lookup(K key);

    void update(K key, V value);
}
