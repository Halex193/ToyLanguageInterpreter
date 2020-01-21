package exceptions;

import model.expressions.Expression;
import model.types.Type;

public class TypeMismatchException extends ProgramException
{
    public TypeMismatchException(String message)
    {
        super(message);
    }

    public TypeMismatchException(Expression expression, Type expected, Type actual)
    {
        super(String.format("Expected type for expression '%s' is %s, but was evaluated to %s", expression, expected, actual));
    }
}
