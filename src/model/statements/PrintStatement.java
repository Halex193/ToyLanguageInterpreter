package model.statements;

import exceptions.ProgramException;
import model.expressions.Expression;
import model.programstate.ProgramState;
import model.values.Value;

public class PrintStatement implements Statement
{
    private Expression expression;

    public PrintStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        Value value = expression.evaluate(programState.getSymbolTable());
        programState.getProgramOutput().add(value);
        return programState;
    }

    @Override
    public Statement deepCopy()
    {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }
}
