package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;

public class ReturnStatement implements Statement
{
    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        programState.getSymbolTableStack().pop();
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return null;
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "return";
    }
}
