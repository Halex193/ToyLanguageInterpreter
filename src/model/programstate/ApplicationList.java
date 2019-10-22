package model.programstate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ApplicationList<T> implements IApplicationList<T>
{
    private List<T> list;

    public ApplicationList()
    {
        list = new ArrayList<>();
    }

    @Override
    public void add(T value)
    {
        list.add(value);
    }

    @Override
    public String toString()
    {
        StringJoiner joiner = new StringJoiner("|", "{", "}");
        list.forEach((element) -> joiner.add(element.toString()));
        return joiner.toString();
    }
}
