package model.programstate;

import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationIndex<T> extends ApplicationDictionary<Integer, T> implements IApplicationIndex<T>
{

    private AtomicInteger addressSeed = new AtomicInteger(1);

    @Override
    public int add(T element)
    {
        int newAddress = addressSeed.getAndIncrement();
        map.put(newAddress, element);
        return newAddress;
    }
}
