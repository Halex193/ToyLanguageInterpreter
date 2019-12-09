package model.expressions;

import exceptions.OperatorNotSupportedException;
import exceptions.OperatorNotValidException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.BoolType;
import model.types.Type;
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

    public LogicExpression(Expression expression1, Expression expression2, String operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = stringToOperator(operator);
    }

    @Override
    public BoolValue evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException
    {
        Value value1 = expression1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new BoolType()))
            throw new OperatorNotSupportedException(value1, operator);

        Value value2 = expression2.evaluate(symbolTable, heap);
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

    @Override
    public Expression deepCopy()
    {
        return new LogicExpression(expression1.deepCopy(), expression2.deepCopy(), operator);
    }

    @Override
    public Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);

        if (!type1.equals(new BoolType()))
        {
            throw new TypeMismatchException(expression1, new BoolType(), type1);
        }
        if (!type2.equals(new BoolType()))
        {
            throw new TypeMismatchException(expression2, new BoolType(), type2);
        }
        return new BoolType();
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
        return Integer.toString(operator);
    }

    public static int stringToOperator(String operator)
    {
        switch (operator)
        {
            case "and":
                return 1;
            case "or":
                return 2;
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
