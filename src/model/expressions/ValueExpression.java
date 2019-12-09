package model.expressions;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression
{
    private Value value;

    public ValueExpression(Value value)
    {
        this.value = value;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap)
    {
        return value;
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return value.getType();
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}
