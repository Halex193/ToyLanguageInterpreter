package model.statements;

import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class NewLatchStatement implements Statement
{
    private String var;
    private Expression expression;

    public NewLatchStatement(String var, Expression expression)
    {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        Value value = expression.evaluate(symbolTable, programState.getHeap());
        if (!value.getType().equals(new IntType()))
        {
            throw new ParameterTypeMismatchException(new IntType(), value);
        }

        Value lookup = symbolTable.lookup(var);
        if (lookup == null)
        {
            throw new VariableNotDeclaredException(var);
        }

        if (!lookup.getType().equals(new IntType()))
        {
            throw new ParameterTypeMismatchException(new IntType(), lookup);
        }

        int number = ((IntValue) value).getValue();
        int address;
        synchronized (programState.getLatchTable())
        {
            address = programState.getLatchTable().add(number);
        }
        symbolTable.update(var, new IntValue(address));
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new NewLatchStatement(var, expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = expression.typeCheck(typeEnvironment);
        if (!type.equals(new IntType()))
        {
            throw new TypeMismatchException(expression, new IntType(), type);
        }

        Type lookup = typeEnvironment.lookup(var);
        if (lookup == null)
        {
            throw new TypeMismatchException("Variable not declared");
        }
        if (!lookup.equals(new IntType()))
        {
            throw new TypeMismatchException("Variable is not of type int");
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("%s=newLatch(%s)", var, expression.toString());
    }
}
