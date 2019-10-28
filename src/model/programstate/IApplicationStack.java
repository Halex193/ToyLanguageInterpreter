package model.programstate;

import exceptions.EmptyApplicationStackException;

public interface IApplicationStack<T>
{
    void push(T element);

    T pop() throws EmptyApplicationStackException;

    int size();

    boolean isEmpty();
}
