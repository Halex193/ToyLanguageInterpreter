package model.expressions;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.Type;
import model.values.Value;

public interface Expression
{
    Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws ProgramException;

    Expression deepCopy();

    Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException;
}
