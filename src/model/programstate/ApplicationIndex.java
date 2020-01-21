package model.programstate;

import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationIndex<T> extends ApplicationDictionary<Integer, T>
{

    private AtomicInteger addressSeed = new AtomicInteger(1);

    public int add(T element)
    {
        int newAddress = addressSeed.getAndIncrement();
        map.put(newAddress, element);
        return newAddress;
    }
}
