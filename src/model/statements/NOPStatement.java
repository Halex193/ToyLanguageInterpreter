package model.statements;

import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;

public class NOPStatement implements Statement
{
    @Override
    public ProgramState execute(ProgramState programState)
    {
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new NOPStatement();
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return "nop";
    }
}
