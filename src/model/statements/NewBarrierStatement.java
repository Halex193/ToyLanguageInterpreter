package model.statements;

import exceptions.AssignTypeMismatchException;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import javafx.util.Pair;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.Vector;

public class NewBarrierStatement implements Statement
{
    private String var;
    private Expression expression;

    public NewBarrierStatement(String var, Expression expression)
    {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {

        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationHeap<Value> heap = programState.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(new IntType()))
        {
            throw new TypeMismatchException(expression, new IntType(), value.getType());
        }
        int number = ((IntValue) value).getValue();
        int address = programState.getBarrierTable().add(new Pair<>(number, new Vector<>()));

        Value lookup = symbolTable.lookup(var);
        if (lookup == null)
            throw new VariableNotDeclaredException(var);

        if(!lookup.getType().equals(new IntType()))
            throw new AssignTypeMismatchException(lookup, new IntValue(address));

        symbolTable.update(var, new IntValue(address));
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new NewBarrierStatement(var, expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type type = expression.typeCheck(typeEnvironment);
        if (!type.equals(new IntType()))
        {
            throw new TypeMismatchException(expression, new IntType(), type);
        }

        Type variableType = typeEnvironment.lookup(var);
        if (!variableType.equals(new IntType()))
        {
            throw new TypeMismatchException("Variable not integer");
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return String.format("%s = newBarrier(%s)", var, expression.toString());
    }
}
