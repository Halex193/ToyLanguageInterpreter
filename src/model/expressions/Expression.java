package model.expressions;

import model.programstate.IApplicationDictionary;
import model.values.Value;

public interface Expression
{
    Value evaluate(IApplicationDictionary<String, Value> symbolTable);
}
