package model.programstate;

import java.util.List;

public interface IApplicationList<T>
{
    void add(T value);

    List<T> asList();
}
