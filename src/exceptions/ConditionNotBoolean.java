package exceptions;

import model.expressions.Expression;

public class ConditionNotBoolean extends ProgramException
{
    public ConditionNotBoolean(Expression condition)
    {
        super(condition.toString());
    }
}
