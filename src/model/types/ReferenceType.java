package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type
{
    private Type referencedType;

    public ReferenceType()
    {
    }

    public ReferenceType(Type referencedType)
    {
        this.referencedType = referencedType;
    }

    public Type getReferencedType()
    {
        return referencedType;
    }

    @Override
    public Value defaultValue()
    {
        return new ReferenceValue(0, referencedType);
    }

    @Override
    public Type deepCopy()
    {
        return new ReferenceType(referencedType.deepCopy());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ReferenceType)
        {
            ReferenceType objReference = (ReferenceType) obj;
            if (objReference.getReferencedType() == null)
            {
                return true;
            }
            return referencedType.equals(objReference.getReferencedType());
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        if (referencedType == null)
        {
            return "Ref";
        }
        return String.format("Ref(%s)", referencedType.toString());
    }
}
