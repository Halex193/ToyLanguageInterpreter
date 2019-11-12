package controller;

import exceptions.MemoryDeallocatedException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GarbageCollector
{
    private GarbageCollector()
    {
    }

    public static void collectGarbage(IApplicationHeap<Value> heap, IApplicationDictionary<String, Value> symbolTable)
    {
        Set<ReferenceValue> usedReferences = symbolTable.
                getMap().
                values().
                stream().
                filter((value) -> value instanceof ReferenceValue).
                map(ReferenceValue.class::cast)
                .collect(Collectors.toSet());

        Set<ReferenceValue> transitiveReferences = new HashSet<>();
        usedReferences.forEach(reference ->
        {
            try
            {
                while (reference.getReferencedType() instanceof ReferenceType)
                {
                    reference = (ReferenceValue) heap.read(reference.getAddress());
                    if (!transitiveReferences.add(reference))
                        break;
                }
            } catch (MemoryDeallocatedException ignored)
            {
            }
        });
        usedReferences.addAll(transitiveReferences);
        Set<Integer> usedAddresses = usedReferences.
                stream().
                map(ReferenceValue::getAddress).
                collect(Collectors.toSet());

        Set<Integer> garbage = heap.getMap().
                keySet().
                stream().
                filter(address -> !usedAddresses.contains(address)).
                collect(Collectors.toSet());

        garbage.forEach(address -> deallocate(heap, address));
    }

    private static void deallocate(IApplicationHeap<Value> heap, int address)
    {
        try
        {
            heap.deallocate(address);
        } catch (MemoryDeallocatedException ignored)
        {

        }
    }
}
