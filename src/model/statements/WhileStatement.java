package model.statements;

import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import model.expressions.Expression;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements Statement
{
    private Expression condition;
    private Statement statement;

    public WhileStatement(Expression condition, Statement statement)
    {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        Value conditionValue = condition.evaluate(programState.getSymbolTable(), programState.getHeap());
        if (!conditionValue.getType().equals(new BoolType()))
        {
            throw new ParameterTypeMismatchException(new BoolType(), conditionValue);
        }

        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        BoolValue conditionBoolean = (BoolValue) conditionValue;
        if(conditionBoolean.getValue())
        {
            executionStack.push(this);
            executionStack.push(statement);
        }
        return programState;
    }

    @Override
    public Statement deepCopy()
    {
        return new WhileStatement(condition.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString()
    {
        return String.format("while(%s){ %s }", condition.toString(), statement.toString());
    }
}
