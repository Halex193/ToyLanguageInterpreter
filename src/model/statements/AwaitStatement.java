package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import javafx.util.Pair;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.IApplicationIndex;
import model.programstate.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.List;

public class AwaitStatement implements Statement
{
    private String var;

    public AwaitStatement(String var)
    {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationIndex<Pair<Integer, List<Integer>>> barrierTable = programState.getBarrierTable();

        Value lookup = symbolTable.lookup(var);
        if (lookup == null)
            throw new VariableNotDeclaredException(var);

        if(!lookup.getType().equals(new IntType()))
            throw new TypeMismatchException("Variable is not integer");
        int address = ((IntValue)lookup).getValue();
        Pair<Integer, List<Integer>> pair = barrierTable.lookup(address);
        if (pair == null)
        {
            throw new ProgramException("Address not found in barrier table");
        }
        int barrierNumber = pair.getKey();
        List<Integer> programIds = pair.getValue();

        if (barrierNumber > programIds.size())
        {
            if (!programIds.contains(programState.getId()))
            {
                programIds.add(programState.getId());
            }
            programState.getExecutionStack().push(this);
        }
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new AwaitStatement(var);
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = typeEnvironment.lookup(var);
        if (!type.equals(new IntType()))
        {
            throw new TypeMismatchException("Variable is not integer");
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("await(%s)", var);
    }
}
