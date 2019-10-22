package model.programstate;

import java.util.EmptyStackException;

public interface IApplicationStack<T>
{
    void push(T element);

    T pop() throws EmptyStackException;

    int size();

    boolean isEmpty();
}
