package exceptions;

import model.values.Value;

public class AssignTypeMismatchException extends ProgramException
{
    public AssignTypeMismatchException(Value lookup, Value expressionValue)
    {
        super(String.format(
                "%s %s was assigned to %s %s",
                lookup.getType().toString(),
                lookup.toString(),
                expressionValue.getType().toString(),
                expressionValue.toString()
        ));
    }
}
