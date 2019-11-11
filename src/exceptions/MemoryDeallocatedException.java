package exceptions;

public class MemoryDeallocatedException extends ProgramException
{
    public MemoryDeallocatedException(int address)
    {
        super(String.format("Memory at address %d was deallocated", address));
    }
}
