package model.programstate;

import model.statements.Statement;
import model.values.Value;
import java.io.BufferedReader;

public class ProgramState
{
    private IApplicationStack<Statement> executionStack;
    private IApplicationDictionary<String, Value> symbolTable;
    private IApplicationList<Value> programOutput;
    private IApplicationDictionary<String, BufferedReader> fileTable;
    private Statement program;

    public ProgramState(Statement program)
    {
        this.executionStack = new ApplicationStack<>();
        this.symbolTable = new ApplicationDictionary<>();
        this.programOutput = new ApplicationList<>();
        this.fileTable = new ApplicationDictionary<>();
        this.program = program.deepCopy();
        this.executionStack.push(program);

    }

    public ProgramState(
            IApplicationStack<Statement> executionStack,
            IApplicationDictionary<String, Value> symbolTable,
            IApplicationList<Value> programOutput,
            IApplicationDictionary<String, BufferedReader> fileTable, Statement program
    )
    {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.programOutput = programOutput;
        this.fileTable = fileTable;
        this.program = program.deepCopy();
        this.executionStack.push(program);
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

    public Statement getProgram()
    {
        return program;
    }

    public boolean isFinished()
    {
        return executionStack.isEmpty();
    }

    @Override
    public String toString()
    {
        return String.format(
                "Execution Stack:\n%s\nSymbol Table:\n%s\nProgram output:\n%s\nFile Table:\n%s",
                executionStack.toString(),
                symbolTable.toString(),
                programOutput.toString(),
                fileTable.toString()
        );
    }
}
