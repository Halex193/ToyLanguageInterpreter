package model.statements;

import exceptions.AssignTypeMismatchException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class AllocateHeapStatement implements Statement
{
    private String id;
    private Expression expression;

    public AllocateHeapStatement(String id, Expression expression)
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
        if (!lookup.getType().equals(new ReferenceType(expressionValue.getType())))
        {
            throw new AssignTypeMismatchException(lookup, expressionValue);
        }

        int address = heap.store(expressionValue);
        symbolTable.update(id, new ReferenceValue(address, expressionValue.getType()));
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new AllocateHeapStatement(id, expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type variableType = typeEnvironment.lookup(id);
        Type expressionType = expression.typeCheck(typeEnvironment);
        if (!variableType.equals(new ReferenceType(expressionType)))
        {
            throw new TypeMismatchException(expression, variableType, expressionType);
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("%s = new(%s)", id, expression.toString());
    }
}
