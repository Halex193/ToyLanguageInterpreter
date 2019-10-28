package exceptions;

import model.values.Value;

public class TypeMismatchException extends ProgramException
{
    public TypeMismatchException(String variable, Value lookup, Value expressionValue)
    {
        super(String.format(
                "Variable %s of type %s was assigned to value %s of type %s",
                variable,
                lookup.toString(),
                expressionValue.toString(),
                expressionValue.getType().toString()
        ));
    }
}
