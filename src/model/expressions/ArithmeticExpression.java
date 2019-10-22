package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.OperatorNotSupportedException;
import exceptions.OperatorNotValidException;
import model.programstate.IApplicationDictionary;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public ArithmeticExpression(Expression expression1, Expression expression2, int operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable)
    {
        Value value1 = expression1.evaluate(symbolTable);
        if (!value1.getType().equals(new IntType()))
            throw new OperatorNotSupportedException(value1, operator);

        Value value2 = expression2.evaluate(symbolTable);
        if (!value2.getType().equals(new IntType()))
            throw new OperatorNotSupportedException(value2, operator);

        int number1 = ((IntValue) value1).getValue();
        int number2 = ((IntValue) value2).getValue();

        switch (operator)
        {
            case 1:
                return new IntValue(number1 + number2);
            case 2:
                return new IntValue(number1 - number2);
            case 3:
                return new IntValue(number1 * number2);
            case 4:
                if (number2 == 0)
                    throw new DivisionByZeroException();
                return new IntValue(number1 / number2);
        }
        throw new OperatorNotValidException(operator);
    }

    public static String operatorToString(int operator)
    {
        switch (operator)
        {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return "*";
            case 4:
                return "/";
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
