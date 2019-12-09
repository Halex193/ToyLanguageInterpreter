package model.programstate;

import java.util.*;

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
    public void invalidate()
    {
        stack.clear();
    }

    @Override
    public List<T> asList()
    {
        List<T> result = new LinkedList<>(stack);
        Collections.reverse(result);
        return result;
    }

    @Override
    public String toString()
    {
        if (stack.isEmpty())
        {
            return "\u2205";
        }
        StringJoiner joiner = new StringJoiner("\n");
        asList().forEach((element) -> joiner.add(element.toString()));
        return joiner.toString();
    }
}
