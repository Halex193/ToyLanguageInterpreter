package model.statements;

import model.programstate.ProgramState;

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
    public String toString()
    {
        return "nop";
    }
}
