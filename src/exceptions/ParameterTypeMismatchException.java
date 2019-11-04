package exceptions;

import model.types.StringType;
import model.types.Type;
import model.values.Value;

public class ParameterTypeMismatchException extends ProgramException
{
    public ParameterTypeMismatchException(Type expected, Value value)
    {
        super(String.format("Parameter of type %s was used instead of type %s", value.getType().toString(), expected.toString()));
    }
}
