package model.statements;

import model.programstate.ApplicationStack;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;

public class CompoundStatement implements Statement
{
    private Statement firstStatement;
    private Statement secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement)
    {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState)
    {
        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return programState;
    }

    @Override
    public String toString()
    {
        return firstStatement + ";" + secondStatement;
    }
}
