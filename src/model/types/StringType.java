package model.types;

import model.values.StringValue;

public class StringType implements Type
{
    @Override
    public StringValue defaultValue()
    {
        return new StringValue();
    }

    @Override
    public Type deepCopy()
    {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof StringType;
    }

    @Override
    public String toString()
    {
        return "String";
    }
}
