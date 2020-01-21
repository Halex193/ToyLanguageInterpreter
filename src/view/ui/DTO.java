package view.ui;

import java.util.List;

public class DTO
{
    int index;
    int value;
    List<Integer> ids;

    public DTO(int index, int value, List<Integer> ids)
    {
        this.index = index;
        this.value = value;
        this.ids = ids;
    }

    public int getIndex()
    {
        return index;
    }

    public int getValue()
    {
        return value;
    }

    public List<Integer> getIds()
    {
        return ids;
    }
}
