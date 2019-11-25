package controller;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller
{
    private static final boolean LOG_PROGRAM_STATE = true;

    private IRepository repository;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public Controller(IRepository repository)
    {
        this.repository = repository;
    }

    public void oneStepForAllPrograms() throws ProgramException
    {
        List<ProgramState> programList = repository.getProgramList();
        if (LOG_PROGRAM_STATE)
        {
            programList.forEach(repository::logProgramState);
        }
        List<Callable<ProgramState>> callables = programList.stream().
                map(programState -> (Callable<ProgramState>) programState::oneStep).
                collect(Collectors.toList());
        try
        {
            List<ProgramState> newProgramStates = executor.invokeAll(callables).stream().map(programStateFuture ->
            {
                try
                {
                    return programStateFuture.get();
                }
                catch (InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            programList.addAll(newProgramStates);
            repository.setProgramList(programList);
            //TODO check this
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates)
    {
        return programStates.stream().
                filter(ProgramState::isNotCompleted).
                collect(Collectors.toList());
    }
}
