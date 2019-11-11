package model.expressions;

import exceptions.ProgramException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.values.Value;

public interface Expression
{
    Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException;

    Expression deepCopy();
}
