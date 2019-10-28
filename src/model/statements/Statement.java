package model.statements;

import exceptions.ProgramException;
import model.programstate.ProgramState;

public interface Statement
{
    ProgramState execute(ProgramState programState) throws ProgramException;

    Statement deepCopy();
}
