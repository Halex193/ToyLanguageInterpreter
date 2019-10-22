package model.statements;

import model.programstate.ProgramState;

public interface Statement
{
    ProgramState execute(ProgramState programState);
}
