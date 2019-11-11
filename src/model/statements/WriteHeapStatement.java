package model.statements;

import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import exceptions.VariableNotDeclaredException;
import exceptions.VariableNotReferenceException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.ReferenceType;
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
        heap.read(referenceValue.getReferenceAddress());

        if (!referenceValue.getReferencedType().equals(expressionValue.getType()))
        {
            throw new ParameterTypeMismatchException(referenceValue.getReferencedType(), expressionValue);
        }
        heap.store(referenceValue.getReferenceAddress(), expressionValue);
        return programState;
    }

    @Override
    public Statement deepCopy()
    {
        return new WriteHeapStatement(id, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return String.format("writeHeap(%s, %s)", id, expression.toString());
    }
}
