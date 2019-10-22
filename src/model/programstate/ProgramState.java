package model.programstate;

import model.statements.Statement;
import model.values.Value;

public class ProgramState
{
    private IApplicationStack<Statement> executionStack;
    private IApplicationDictionary<String, Value> symbolTable;
    private IApplicationList<Value> programOutput;
    private Statement program;

    public ProgramState(Statement program)
    {
        this.executionStack = new ApplicationStack<>();
        this.symbolTable = new ApplicationDictionary<>();
        this.programOutput = new ApplicationList<>();
        this.program = program;
        this.executionStack.push(program);

    }

    public ProgramState(
            IApplicationStack<Statement> executionStack,
            IApplicationDictionary<String, Value> symbolTable,
            IApplicationList<Value> programOutput,
            Statement program
    )
    {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.programOutput = programOutput;
        this.program = program;
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

    public Statement getProgram()
    {
        return program;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Execution Stack : %s\nSymbol Table : %s\nProgram output : %s\n",
                executionStack.toString(),
                symbolTable.toString(),
                programOutput.toString()
        );
    }
}
