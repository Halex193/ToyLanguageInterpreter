package exceptions;

public class OperatorNotValidException extends ProgramException
{
    public OperatorNotValidException(int operator)
    {
        super(Integer.toString(operator));
    }
}
