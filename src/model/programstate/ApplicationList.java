package model.programstate;

import java.util.*;

public class ApplicationList<T> implements IApplicationList<T>
{
    private List<T> list;

    public ApplicationList()
    {
        list = new Vector<>();
    }

    @Override
    public void add(T value)
    {
        list.add(value);
    }

    @Override
    public List<T> asList()
    {
        return new ArrayList<>(list);
    }

    @Override
    public String toString()
    {
        if (list.isEmpty())
        {
            return "\u2205";
        }
        StringJoiner joiner = new StringJoiner("\n");
        list.forEach((element) -> joiner.add(element.toString()));
        return joiner.toString();
    }
}
