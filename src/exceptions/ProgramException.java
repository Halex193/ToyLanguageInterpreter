package exceptions;

public class ProgramException extends RuntimeException
{
    public ProgramException()
    {
    }

    public ProgramException(String message)
    {
        super(message);
    }
}