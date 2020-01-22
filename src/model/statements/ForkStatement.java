package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import javafx.util.Pair;
import model.programstate.*;
import model.types.Type;
import model.values.Value;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ForkStatement implements Statement
{
    private Statement statement;

    public ForkStatement(Statement statement)
    {
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationStack<Statement> executionStack = new ApplicationStack<>();
        List<IApplicationDictionary<String, Value>> newSymbolTables = programState.getSymbolTableStack().asList().stream().map(IApplicationDictionary::shallowCopy).collect(Collectors.toList());
        Collections.reverse(newSymbolTables);
        IApplicationStack<IApplicationDictionary<String, Value>> symbolTableStack = new ApplicationStack<>();
        newSymbolTables.forEach(symbolTableStack::push);
        IApplicationList<Value> programOutput = programState.getProgramOutput();
        IApplicationDictionary<String, BufferedReader> fileTable = programState.getFileTable();
        IApplicationHeap<Value> heap = programState.getHeap();
        IApplicationIndex<Pair<Integer, List<Integer>>> barrierTable = programState.getBarrierTable();
        IApplicationDictionary<String, Pair<List<String>, Statement>> procedureTable = programState.getProcedureTable();

        return new ProgramState(executionStack, symbolTableStack, programOutput, fileTable, heap, barrierTable, procedureTable, statement);
    }

    @Override
    public Statement deepCopy()
    {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        statement.typeCheck(typeEnvironment.shallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("fork{ %s }", statement.toString());
    }
}
