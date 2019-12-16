package model.programstate;

import exceptions.MemoryDeallocatedException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationHeap<T> implements IApplicationHeap<T>
{
    private AtomicInteger addressSeed = new AtomicInteger(1);
    private Map<Integer, T> memoryMap = new ConcurrentHashMap<>();

    @Override
    public T read(int address) throws MemoryDeallocatedException
    {
        T lookup = memoryMap.get(address);
        if (lookup == null)
        {
            throw new MemoryDeallocatedException(address);
        }
        return lookup;
    }

    @Override
    public int store(T value)
    {
        int newAddress = addressSeed.getAndIncrement();
        memoryMap.put(newAddress, value);
        return newAddress;
    }

    @Override
    public void store(int address, T value)
    {
        memoryMap.put(address, value);
    }

    @Override
    public void deallocate(int address) throws MemoryDeallocatedException
    {
        if (address == 0)
        {
            throw new MemoryDeallocatedException(address);
        }
        T lookup = memoryMap.get(address);
        if (lookup == null)
        {
            throw new MemoryDeallocatedException(address);
        }
        memoryMap.remove(address);
    }

    @Override
    public Map<Integer, T> getMap()
    {
        return memoryMap;
    }

    @Override
    public String toString()
    {
        if (memoryMap.isEmpty())
        {
            return "\u2205";
        }
        StringJoiner joiner = new StringJoiner("\n");
        memoryMap.forEach((key, value) -> joiner.add(String.format("%s --> %s", key, value)));
        return joiner.toString();
    }
}
