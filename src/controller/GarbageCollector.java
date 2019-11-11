package controller;

import exceptions.MemoryDeallocatedException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.Set;
import java.util.stream.Collectors;

public class GarbageCollector
{
    private GarbageCollector()
    {
    }

    public static void collectGarbage(IApplicationHeap<Value> heap, IApplicationDictionary<String, Value> symbolTable)
    {
        symbolTable.
                getMap().
                values().
                stream().
                filter((value) -> value instanceof ReferenceValue).
                map(ReferenceValue.class::cast)
                .forEach(reference -> deallocate(heap, reference));
    }

    private static void deallocate(IApplicationHeap<Value> heap, ReferenceValue referenceValue)
    {
        try
        {
            heap.deallocate(referenceValue.getAddress());
            if (referenceValue.getReferencedType() instanceof ReferenceType)
            {
                Value reference = heap.read(referenceValue.getAddress());
                deallocate(heap, (ReferenceValue) reference);
            }
        }
        catch (MemoryDeallocatedException ignored)
        {

        }
    }
}
