package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;

public interface Statement
{
    ProgramState execute(ProgramState programState) throws ProgramException;

    Statement deepCopy();

    IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException;
}
