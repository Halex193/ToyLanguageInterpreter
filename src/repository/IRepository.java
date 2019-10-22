package repository;

import model.programstate.ProgramState;

public interface IRepository
{
    ProgramState getCurrentProgram();

    void setCurrentProgram(ProgramState programState);

    ProgramState getProgramState(int number);
}
