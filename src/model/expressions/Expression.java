package model.expressions;

import exceptions.ProgramException;
import model.programstate.IApplicationDictionary;
import model.values.Value;

public interface Expression
{
    Value evaluate(IApplicationDictionary<String, Value> symbolTable) throws ProgramException;

    Expression deepCopy();
}
