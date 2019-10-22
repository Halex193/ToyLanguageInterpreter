package model.expressions;

import exceptions.VariableNotDeclared;
import model.programstate.IApplicationDictionary;
import model.values.Value;

public class VariableExpression implements Expression
{
    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable)
    {
        Value lookup = symbolTable.lookup(id);
        if (lookup == null)
            throw new VariableNotDeclared(id);
        return lookup;
    }

    @Override
    public String toString()
    {
        return id;
    }
}
