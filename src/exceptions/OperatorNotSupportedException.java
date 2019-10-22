package exceptions;

import model.expressions.ArithmeticExpression;
import model.values.Value;

public class OperatorNotSupportedException extends ProgramException
{
    public OperatorNotSupportedException(Value value, int operator)
    {
        super(String.format("Operator %s cannot be applied to value %s of type %s",
                            ArithmeticExpression.operatorToString(operator),
                            value.toString(),
                            value.getType().toString()
        ));
    }
}
