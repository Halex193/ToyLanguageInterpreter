package model.statements;

import exceptions.*;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class WriteHeapStatement implements Statement
{
    private String id;
    private Expression expression;

    public WriteHeapStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationHeap<Value> heap = programState.getHeap();

        Value lookup = symbolTable.lookup(id);
        if (lookup == null)
        {
            throw new VariableNotDeclaredException(id);
        }

        Value expressionValue = expression.evaluate(symbolTable, heap);
        if(!lookup.getType().equals(new ReferenceType()))
        {
            throw new VariableNotReferenceException(id);
        }

        ReferenceValue referenceValue = (ReferenceValue) lookup;
        heap.read(referenceValue.getAddress());

        if (!referenceValue.getReferencedType().equals(expressionValue.getType()))
        {
            throw new ParameterTypeMismatchException(referenceValue.getReferencedType(), expressionValue);
        }
        heap.store(referenceValue.getAddress(), expressionValue);
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new WriteHeapStatement(id, expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("writeHeap(%s, %s)", id, expression.toString());
    }
}
