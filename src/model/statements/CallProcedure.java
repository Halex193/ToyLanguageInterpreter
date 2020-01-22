package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import javafx.util.Pair;
import model.expressions.Expression;
import model.programstate.*;
import model.types.Type;
import model.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallProcedure implements Statement
{
    private String name;
    private List<Expression> parameters;

    public CallProcedure(String name, List<Expression> parameters)
    {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Pair<List<String>, Statement>> procedureTable = programState.getProcedureTable();
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationHeap<Value> heap = programState.getHeap();
        Pair<List<String>, Statement> lookup = procedureTable.lookup(name);
        if (lookup == null)
        {
            throw new ProgramException(String.format("Procedure %s undefined", name));
        }
        List<String> variables = lookup.getKey();
        Statement statement = lookup.getValue();
        IApplicationDictionary<String, Value> newSymbolTable = new ApplicationDictionary<>();
        for (int i=0;i<parameters.size();i++)
        {
            newSymbolTable.update(variables.get(i), parameters.get(i).evaluate(symbolTable, heap));
        }
        programState.getSymbolTableStack().push(newSymbolTable);
        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        executionStack.push(new ReturnStatement());
        executionStack.push(statement);
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new CallProcedure(name, new ArrayList<>(parameters));
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("call %s(%s)", name, parameters.stream().map(Expression::toString).collect(Collectors.joining(", ")));
    }
}
