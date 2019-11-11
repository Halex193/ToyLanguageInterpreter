package model.statements;

import exceptions.FileIOException;
import exceptions.ParameterTypeMismatchException;
import exceptions.ProgramException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement implements Statement
{
    private Expression fileNameExpression;

    public OpenRFileStatement(Expression fileNameExpression)
    {
        this.fileNameExpression = fileNameExpression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationDictionary<String, BufferedReader> fileTable = programState.getFileTable();
        IApplicationHeap<Value> heap = programState.getHeap();

        Value value = fileNameExpression.evaluate(symbolTable, heap);
        if (!value.getType().equals(new StringType()))
        {
            throw new ParameterTypeMismatchException(new StringType(), value);
        }

        String fileName = ((StringValue) value).getValue();

        BufferedReader reader = fileTable.lookup(fileName);
        if (reader != null)
        {
            throw new FileIOException(String.format("File '%s' already opened", fileName));
        }

        try
        {
            reader = new BufferedReader(new FileReader(fileName));
            fileTable.update(fileName, reader);
        }
        catch (FileNotFoundException e)
        {
            throw new FileIOException(String.format("File '%s' not found", fileName));
        }
        return programState;
    }

    @Override
    public Statement deepCopy()
    {
        return new OpenRFileStatement(fileNameExpression.deepCopy());
    }

    @Override
    public String toString()
    {
        return String.format("openRFile(%s)", fileNameExpression.toString());
    }
}
