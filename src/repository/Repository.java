package repository;

import java.util.ArrayList;
import java.util.List;

import model.programstate.ProgramState;

public class Repository implements IRepository
{
    private List<ProgramState> programStates;

    public Repository()
    {
        programStates = new ArrayList<>(1);
    }

    public Repository(List<ProgramState> programStates)
    {
        this.programStates = programStates;
    }

    @Override
    public ProgramState getCurrentProgram()
    {
        return programStates.get(0);
    }

    @Override
    public void setCurrentProgram(ProgramState programState)
    {
        if (programStates.isEmpty())
            programStates.add(programState);
        else
            programStates.set(0, programState);
    }

    @Override
    public ProgramState getProgramState(int number)
    {
        return programStates.get(number);
    }
}
