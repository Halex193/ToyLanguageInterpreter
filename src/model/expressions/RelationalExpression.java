package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.OperatorNotSupportedException;
import exceptions.OperatorNotValidException;
import exceptions.ProgramException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public RelationalExpression(Expression expression1, Expression expression2, int operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    public RelationalExpression(Expression expression1, Expression expression2, String operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = stringToOperator(operator);
    }

    @Override
    public BoolValue evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException
    {
        Value value1 = expression1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new IntType()))
            throw new OperatorNotSupportedException(value1, operator);

        Value value2 = expression2.evaluate(symbolTable, heap);
        if (!value2.getType().equals(new IntType()))
            throw new OperatorNotSupportedException(value2, operator);

        int number1 = ((IntValue) value1).getValue();
        int number2 = ((IntValue) value2).getValue();

        switch (operator)
        {
            case 1:
                return new BoolValue(number1 < number2);
            case 2:
                return new BoolValue(number1 <= number2);
            case 3:
                return new BoolValue(number1 == number2);
            case 4:
                return new BoolValue(number1 != number2);
            case 5:
                return new BoolValue(number1 > number2);
            case 6:
                return new BoolValue(number1 >= number2);
        }
        throw new OperatorNotValidException(operator);
    }

    @Override
    public Expression deepCopy()
    {
        return new RelationalExpression(expression1.deepCopy(), expression2.deepCopy(), operator);
    }

    public static String operatorToString(int operator)
    {
        switch (operator)
        {
            case 1:
                return "<";
            case 2:
                return "<=";
            case 3:
                return "==";
            case 4:
                return "!=";
            case 5:
                return ">";
            case 6:
                return ">=";
        }
        return Integer.toString(operator);
    }

    public static int stringToOperator(String operator)
    {
        switch (operator)
        {
            case "<":
                return 1;
            case "<=":
                return 2;
            case "==":
                return 3;
            case "!=":
                return 4;
            case ">":
                return 5;
            case ">=":
                return 6;
        }
        return 0;
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
