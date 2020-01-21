package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.expressions.Expression;
import model.expressions.NegationExpression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class RepeatStatement implements Statement
{
    private Statement statement;
    private Expression condition;

    public RepeatStatement(Statement statement, Expression condition)
    {
        this.statement = statement;
        this.condition = condition;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        Statement whileStatement = new WhileStatement(new NegationExpression(condition), statement);
        executionStack.push(new CompoundStatement(statement, whileStatement));
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new RepeatStatement(statement.deepCopy(), condition.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = condition.typeCheck(typeEnvironment);
        if(!type.equals(new BoolType()))
        {
            throw new TypeMismatchException(condition, new BoolType(), type);
        }
        statement.typeCheck(typeEnvironment.shallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("repeat { %s } until %s", statement.toString(), condition.toString());
    }
}
