package exceptions;

public class VariableAlreadyDeclaredException extends ProgramException
{
    public VariableAlreadyDeclaredException(String id)
    {
        super(id);
    }
}
