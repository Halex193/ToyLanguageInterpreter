package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value
{
    private int referenceAddress;
    private Type referencedType;

    public ReferenceValue(int referenceAddress, Type referencedType)
    {
        this.referenceAddress = referenceAddress;
        this.referencedType = referencedType;
    }

    public int getReferenceAddress()
    {
        return referenceAddress;
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
        return new ReferenceValue(referenceAddress, referencedType.deepCopy());
    }
}
