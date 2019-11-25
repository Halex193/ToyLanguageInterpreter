package exceptions;

public class ProgramException extends Exception
{
    private int programStateId = -1;

    public ProgramException()
    {
    }

    public ProgramException(String message)
    {
        super(message);
    }

    public void setProgramStateId(int programStateId)
    {
        this.programStateId = programStateId;
    }

    public int getProgramStateId()
    {
        return programStateId;
    }
}