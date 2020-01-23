package model.statements;

import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationIndex;
import model.programstate.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class AwaitStatement implements Statement
{
    private String var;

    public AwaitStatement(String var)
    {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationIndex<Integer> latchTable = programState.getLatchTable();

        Value lookup = symbolTable.lookup(var);
        if (lookup == null)
        {
            throw new VariableNotDeclaredException(var);
        }

        if (!lookup.getType().equals(new IntType()))
        {
            throw new ParameterTypeMismatchException(new IntType(), lookup);
        }

        int address = ((IntValue) lookup).getValue();
        Integer count;
        synchronized (programState.getLatchTable())
        {
            count = latchTable.lookup(address);
        }
        if (count == null)
        {
            throw new ProgramException("Index not found in latch table");
        }
        if (count != 0)
        {
            programState.getExecutionStack().push(this);
        }
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new AwaitStatement(var);
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type lookup = typeEnvironment.lookup(var);
        if (lookup == null)
        {
            throw new TypeMismatchException("Variable not declared");
        }
        if (!lookup.equals(new IntType()))
        {
            throw new TypeMismatchException("Variable is not int");
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("await(%s)", var);
    }
}
