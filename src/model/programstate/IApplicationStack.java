package model.programstate;

import exceptions.EmptyApplicationStackException;
import model.statements.Statement;

import java.util.List;

public interface IApplicationStack<T>
{
    void push(T element);

    T pop() throws EmptyApplicationStackException;

    int size();

    boolean isEmpty();

    void invalidate();

    List<T> asList();

    T peek();
}
