package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value
{
    private String value;

    public StringValue()
    {
    }

    public StringValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public Type getType()
    {
        return new StringType();
    }

    @Override
    public Value deepCopy()
    {
        return new StringValue(value);
    }

    @Override
    public String toString()
    {
        return String.format("\"%s\"", value);
    }
}
