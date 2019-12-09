package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.Type;
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
        Value value = expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        programState.getProgramOutput().add(value);
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }
}
