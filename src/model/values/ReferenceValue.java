package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value
{
    private int address;
    private Type referencedType;

    public ReferenceValue(int address, Type referencedType)
    {
        this.address = address;
        this.referencedType = referencedType;
    }

    public int getAddress()
    {
        return address;
    }

    public Type getReferencedType()
    {
        return referencedType;
    }

    @Override
    public Type getType()
    {
        return new ReferenceType(referencedType);
    }

    @Override
    public Value deepCopy()
    {
        return new ReferenceValue(address, referencedType.deepCopy());
    }
}
