package model.expressions;

import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeapExpression implements Expression
{
    private Expression expression;

    public ReadHeapExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException
    {
        Value expressionValue = expression.evaluate(symbolTable, heap);
        if(!expressionValue.getType().equals(new ReferenceType()))
        {
            throw new ParameterTypeMismatchException(new ReferenceType(), expressionValue);
        }
        ReferenceValue referenceValue = (ReferenceValue) expressionValue;
        return heap.read(referenceValue.getAddress());
    }

    @Override
    public Expression deepCopy()
    {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = expression.typeCheck(typeEnvironment);

        if (!(type instanceof ReferenceType))
        {
            throw new TypeMismatchException(expression, new ReferenceType(), type);
        }
        Type referencedType = ((ReferenceType) type).getReferencedType();
        return new ReferenceType(referencedType);
    }

    @Override
    public String toString()
    {
        return String.format("readHeap(%s)", expression.toString());
    }
}
