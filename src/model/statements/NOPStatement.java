package model.statements;

import model.programstate.ProgramState;

public class NOPStatement implements Statement
{
    @Override
    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }

    @Override
    public String toString()
    {
        return "nop";
    }
}
