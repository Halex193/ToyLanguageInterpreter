package model.statements;

import exceptions.FileIOException;
import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import exceptions.VariableNotDeclaredException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement
{
    private Expression fileNameExpression;
    private String id;

    public ReadFileStatement(Expression fileNameExpression, String id)
    {
        this.fileNameExpression = fileNameExpression;
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationDictionary<String, BufferedReader> fileTable = programState.getFileTable();
        IApplicationHeap<Value> heap = programState.getHeap();

        Value variableValue = symbolTable.lookup(id);
        if (variableValue == null)
        {
            throw new VariableNotDeclaredException(id);
        }

        if (!variableValue.getType().equals(new IntType()))
        {
            throw new ParameterTypeMismatchException(new IntType(), variableValue);
        }

        Value expressionValue = fileNameExpression.evaluate(symbolTable, heap);
        if (!expressionValue.getType().equals(new StringType()))
        {
            throw new ParameterTypeMismatchException(new StringType(), expressionValue);
        }

        String fileName = ((StringValue) expressionValue).getValue();

        BufferedReader reader = fileTable.lookup(fileName);
        if (reader == null)
        {
            throw new FileIOException(String.format("File '%s' was not opened", fileName));
        }

        try
        {
            String line = reader.readLine();
            int value = Integer.parseInt(line);
            symbolTable.update(id, new IntValue(value));
        }
        catch (IOException e)
        {
            throw new FileIOException(String.format("File '%s' could not be read", fileName));
        }
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new ReadFileStatement(fileNameExpression.deepCopy(), id);
    }

    @Override
    public String toString()
    {
        return String.format("readFile(%s, %s)", fileNameExpression.toString(), id);
    }
}
