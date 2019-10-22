package model.expressions;

import exceptions.OperatorNotSupportedException;
import exceptions.OperatorNotValidException;
import model.programstate.IApplicationDictionary;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public LogicExpression(Expression expression1, Expression expression2, int operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable)
    {
        Value value1 = expression1.evaluate(symbolTable);
        if (!value1.getType().equals(new BoolType()))
            throw new OperatorNotSupportedException(value1, operator);

        Value value2 = expression2.evaluate(symbolTable);
        if (!value2.getType().equals(new BoolType()))
            throw new OperatorNotSupportedException(value2, operator);

        boolean bool1 = ((BoolValue) value1).getValue();
        boolean bool2 = ((BoolValue) value2).getValue();

        switch (operator)
        {
            case 1:
                return new BoolValue(bool1 && bool2);
            case 2:
                return new BoolValue(bool1 || bool2);
        }
        throw new OperatorNotValidException(operator);
    }

    public static String operatorToString(int operator)
    {
        switch (operator)
        {
            case 1:
                return "and";
            case 2:
                return "or";
        }
        throw new OperatorNotValidException(operator);
    }

    @Override
    public String toString()
    {
        return String.format(
                "%s %s %s",
                expression1.toString(),
                operatorToString(operator),
                expression2.toString()
        );
    }
}
