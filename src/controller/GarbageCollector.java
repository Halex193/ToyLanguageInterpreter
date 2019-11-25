package controller;

import exceptions.MemoryDeallocatedException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector
{
    private GarbageCollector()
    {
    }

    public static void collectGarbage(IApplicationHeap<Value> heap, Set<ReferenceValue> usedReferences)
    {
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

    public static void collectGarbage(List<ProgramState> programStates)
    {
        Optional<ProgramState> anyProgramState = programStates.stream().findAny();
        if (anyProgramState.isEmpty())
        {
            return;
        }

        Set<ReferenceValue> usedReferences = programStates.stream().
                map(ProgramState::getSymbolTable).
                map(IApplicationDictionary::getMap).
                map(Map::values).
                map(Collection::stream).
                reduce(Stream.empty(), Stream::concat).
                filter((value) -> value instanceof ReferenceValue).
                map(ReferenceValue.class::cast).
                collect(Collectors.toSet());

        collectGarbage(anyProgramState.get().getHeap(), usedReferences);
    }
}
