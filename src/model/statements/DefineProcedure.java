package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import javafx.util.Pair;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;

import java.util.ArrayList;
import java.util.List;

public class DefineProcedure implements Statement
{
    private String name;
    private List<String> variableNames;
    private Statement statement;

    public DefineProcedure(String name, List<String> variableNames, Statement statement)
    {
        this.name = name;
        this.variableNames = variableNames;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        programState.getProcedureTable().update(name, new Pair<>(variableNames, statement));
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new DefineProcedure(name, new ArrayList<>(variableNames), statement.deepCopy());
    }

    @Override
    public String toString()
    {
        String parameterList = String.join(", ", variableNames);
        return String.format("procedure %s(%s) { %s }", name, parameterList, statement.toString());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return null;
    }
}
