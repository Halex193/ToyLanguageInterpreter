package repository;

import exceptions.ProgramException;
import model.programstate.ProgramState;

import java.util.List;

public interface IRepository
{
    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> programStates);

    void logProgramState(ProgramState state);

    void logRepository();

    void logException(ProgramException exception);
}
