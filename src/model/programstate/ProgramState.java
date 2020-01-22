package model.programstate;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import javafx.util.Pair;
import model.statements.Statement;
import model.values.Value;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramState
{
    private IApplicationStack<Statement> executionStack;

    private IApplicationStack<IApplicationDictionary<String, Value>> symbolTableStack;

    private IApplicationList<Value> programOutput;
    private IApplicationDictionary<String, BufferedReader> fileTable;
    private IApplicationHeap<Value> heap;
    private IApplicationIndex<Pair<Integer, List<Integer>>> barrierTable;
    private IApplicationDictionary<String, Pair<List<String>, Statement>> procedureTable;

    public IApplicationDictionary<String, Pair<List<String>, Statement>> getProcedureTable()
    {
        return procedureTable;
    }

    private Statement program;
    private int id;
    private static int idSeed = 1;

    public ProgramState(Statement program)
    {
        this.id = generateId();
        this.executionStack = new ApplicationStack<>();
        this.symbolTableStack = new ApplicationStack<>();
        this.programOutput = new ApplicationList<>();
        this.fileTable = new ApplicationDictionary<>();
        this.heap = new ApplicationHeap<>();
        this.barrierTable = new ApplicationIndex<>();
        this.procedureTable = new ApplicationDictionary<>();
        this.program = program.deepCopy();
        this.executionStack.push(program);
        symbolTableStack.push(new ApplicationDictionary<>());

    }

    public ProgramState(
            IApplicationStack<Statement> executionStack,
            IApplicationStack<IApplicationDictionary<String, Value>> symbolTableStack,
            IApplicationList<Value> programOutput,
            IApplicationDictionary<String, BufferedReader> fileTable,
            IApplicationHeap<Value> heap,
            IApplicationIndex<Pair<Integer, List<Integer>>> barrierTable,
            IApplicationDictionary<String, Pair<List<String>, Statement>> procedureTable,
            Statement program
    )
    {
        this.id = generateId();
        this.executionStack = executionStack;
        this.symbolTableStack = symbolTableStack;
        this.programOutput = programOutput;
        this.fileTable = fileTable;
        this.heap = heap;
        this.program = program.deepCopy();
        this.barrierTable = barrierTable;
        this.procedureTable = procedureTable;
        this.executionStack.push(program);
    }

    private static synchronized int generateId()
    {
        return idSeed++;
    }

    public IApplicationStack<Statement> getExecutionStack()
    {
        return executionStack;
    }

    public IApplicationStack<IApplicationDictionary<String, Value>> getSymbolTableStack()
    {
        return symbolTableStack;
    }

    public IApplicationDictionary<String, Value> getSymbolTable()
    {
        return symbolTableStack.peek();
    }

    public IApplicationList<Value> getProgramOutput()
    {
        return programOutput;
    }

    public IApplicationDictionary<String, BufferedReader> getFileTable()
    {
        return fileTable;
    }

    public IApplicationHeap<Value> getHeap()
    {
        return heap;
    }

    public IApplicationIndex<Pair<Integer, List<Integer>>> getBarrierTable()
    {
        return barrierTable;
    }

    public Statement getProgram()
    {
        return program;
    }

    public int getId()
    {
        return id;
    }

    public boolean isNotCompleted()
    {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws ProgramException
    {
        if (!this.isNotCompleted())
            throw new ProgramFinishedException();
        Statement currentStatement = executionStack.pop();
        try
        {
            return currentStatement.execute(this);
        }
        catch (ProgramException exception)
        {
            executionStack.invalidate();
            exception.setProgramStateId(id);
            throw exception;
        }
    }

    @Override
    public String toString()
    {
        return String.format(
                "Program State ID: %d\nExecution Stack:\n%s\nSymbol Table:\n%s\nProgram output:\n%s\nFile Table:\n%s\nHeap:\n%s\nBarrier Table:\n%s\nProcedure Table:\n%s",
                id,
                executionStack.toString(),
                getSymbolTable().toString(),
                programOutput.toString(),
                fileTable.toString(),
                heap.toString(),
                barrierTable.toString(),
                procedureTable.toString()
        );
    }
}
