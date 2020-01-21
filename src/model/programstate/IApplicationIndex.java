package model.programstate;

public interface IApplicationIndex<T> extends IApplicationDictionary<Integer, T>
{
    int add(T element);
}
