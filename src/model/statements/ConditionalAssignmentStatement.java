package model.statements;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.BoolType;
import model.types.Type;

public class ConditionalAssignmentStatement implements Statement
{
    private String var;
    private Expression expression1;
    private Expression expression2;
    private Expression expression3;

    public ConditionalAssignmentStatement(String var, Expression expression1, Expression expression2, Expression expression3)
    {
        this.var = var;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        Statement statement = new IfStatement(expression1, new AssignStatement(var, expression2), new AssignStatement(var, expression3));
        programState.getExecutionStack().push(statement);
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new ConditionalAssignmentStatement(var, expression1, expression2, expression3);
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type conditionType = expression1.typeCheck(typeEnvironment);
        if (!conditionType.equals(new BoolType()))
        {
            throw new TypeMismatchException(expression1, new BoolType(), conditionType);
        }
        Type expressionType = typeEnvironment.lookup(var);

        Type expression2Type = expression2.typeCheck(typeEnvironment);
        if (!expression2Type.equals(expressionType))
        {
            throw new TypeMismatchException(expression2, expressionType, expression2Type);
        }

        Type expression3Type = expression3.typeCheck(typeEnvironment);
        if (!expression3Type.equals(expressionType))
        {
            throw new TypeMismatchException(expression3, expressionType, expression3Type);
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("%s=%s?%s:%s", var, expression1.toString(), expression2.toString(), expression3.toString());
    }
}
