package model.expressions;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class NegationExpression implements Expression
{
    private Expression expression;

    public NegationExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException
    {
        BoolValue boolValue = (BoolValue) expression.evaluate(symbolTable, heap);
        return new BoolValue(!boolValue.getValue());
    }

    @Override
    public Expression deepCopy()
    {
        return new NegationExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = expression.typeCheck(typeEnvironment);
        if (!type.equals(new BoolType()))
        {
            throw new TypeMismatchException(expression, new BoolType(), type);
        }
        return new BoolType();
    }

    @Override
    public String toString()
    {
        return String.format("!(%s)", expression.toString());
    }
}
