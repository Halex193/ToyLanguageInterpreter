package model.programstate;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;

import exceptions.EmptyApplicationStackException;
import model.statements.Statement;

public class ApplicationStack<T> implements IApplicationStack<T>
{
    private Stack<T> stack;

    public ApplicationStack()
    {
        stack = new Stack<>();
    }

    @Override
    public void push(T element)
    {
        stack.push(element);
    }

    @Override
    public T pop() throws EmptyApplicationStackException
    {
        if (stack.isEmpty())
            throw new EmptyApplicationStackException();
        return stack.pop();
    }

    @Override
    public int size()
    {
        return stack.size();
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public String toString()
    {
        if (stack.isEmpty())
        {
            return "\u2205";
        }
        StringJoiner joiner = new StringJoiner("\n");
        List<T> list = new LinkedList<>(stack);
        Collections.reverse(list);
        list.forEach((element) -> joiner.add(element.toString()));
        return joiner.toString();
    }
}
