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

public class CountDownStatement implements Statement
{
    private String var;

    public CountDownStatement(String var)
    {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();

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

        synchronized (programState.getLatchTable())
        {
            Integer count = programState.getLatchTable().lookup(address);
            if (count == null)
            {
                throw new ProgramException("Index does represent a countdownLatch");
            }
            if (count > 0)
            {
                programState.getLatchTable().update(address, count - 1);
            }
            programState.getProgramOutput().add(new IntValue(programState.getId()));
        }
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new CountDownStatement(var);
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
        return String.format("countDown(%s)", var);
    }
}
