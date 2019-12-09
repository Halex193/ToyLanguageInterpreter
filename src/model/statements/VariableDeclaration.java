package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableAlreadyDeclaredException;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;
import model.values.Value;

public class VariableDeclaration implements Statement
{
    private Type type;
    private String id;

    public VariableDeclaration(Type type, String id)
    {
        this.type = type;
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();

        if(symbolTable.lookup(id) !=null)
            throw new VariableAlreadyDeclaredException(id);

        symbolTable.update(id, type.defaultValue());
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new VariableDeclaration(type.deepCopy(), id);
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        IApplicationDictionary<String, Type> newEnvironment = typeEnvironment.deepCopy();
        newEnvironment.update(id, type);
        return newEnvironment;
    }

    @Override
    public String toString()
    {
        return type.toString() + " " + id;
    }
}
