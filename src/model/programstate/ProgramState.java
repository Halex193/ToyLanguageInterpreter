package model.programstate;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import model.statements.Statement;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState
{
    private IApplicationStack<Statement> executionStack;
    private IApplicationDictionary<String, Value> symbolTable;
    private IApplicationList<Value> programOutput;
    private IApplicationDictionary<String, BufferedReader> fileTable;
    private IApplicationHeap<Value> heap;
    private Statement program;
    private int id;
    private static int idSeed = 1;

    public ProgramState(Statement program)
    {
        this.id = generateId();
        this.executionStack = new ApplicationStack<>();
        this.symbolTable = new ApplicationDictionary<>();
        this.programOutput = new ApplicationList<>();
        this.fileTable = new ApplicationDictionary<>();
        this.heap = new ApplicationHeap<>();
        this.program = program.deepCopy();
        this.executionStack.push(program);

    }

    public ProgramState(
            IApplicationStack<Statement> executionStack,
            IApplicationDictionary<String, Value> symbolTable,
            IApplicationList<Value> programOutput,
            IApplicationDictionary<String, BufferedReader> fileTable,
            IApplicationHeap<Value> heap,
            Statement program
    )
    {
        this.id = generateId();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.programOutput = programOutput;
        this.fileTable = fileTable;
        this.heap = heap;
        this.program = program.deepCopy();
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

    public IApplicationDictionary<String, Value> getSymbolTable()
    {
        return symbolTable;
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
        if (this.isNotCompleted())
            throw new ProgramFinishedException();
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString()
    {
        return String.format(
                "Program State ID: %d\nExecution Stack:\n%s\nSymbol Table:\n%s\nProgram output:\n%s\nFile Table:\n%s\nHeap:\n%s",
                id,
                executionStack.toString(),
                symbolTable.toString(),
                programOutput.toString(),
                fileTable.toString(),
                heap.toString()
        );
    }
}
