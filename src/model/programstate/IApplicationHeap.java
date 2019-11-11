package model.programstate;

import exceptions.MemoryDeallocatedException;
import model.values.Value;

public interface IApplicationHeap<T>
{
    T read(int address) throws MemoryDeallocatedException;

    int store(T value);

    void store(int address, T value);

    void deallocate(int address) throws MemoryDeallocatedException;
}
