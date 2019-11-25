package repository;

import model.programstate.ProgramState;

import java.util.List;

public interface IRepository
{
    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> programStates);

    void logProgramState(ProgramState state);
}
