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
    private ExecutorService executor;

    public Controller(IRepository repository)
    {
        this.repository = repository;
    }

    public void oneStepForAllPrograms()
    {
        List<ProgramState> programList = repository.getProgramList();
        List<Callable<ProgramState>> callables = programList.stream().
                map(programState -> (Callable<ProgramState>) programState::oneStep).
                collect(Collectors.toList());
        try
        {
            List<ProgramState> newProgramStates = executor.invokeAll(callables).stream().
                    map(programStateFuture ->
                    {
                        try
                        {
                            return programStateFuture.get();
                        } catch (ExecutionException e)
                        {
                            if (e.getCause() instanceof ProgramException)
                            {
                                ProgramException exception = (ProgramException) e.getCause();
                                repository.logException(exception);
                            }
                            return null;
                        } catch (InterruptedException e)
                        {
                            return null;
                        }
                    }).filter(Objects::nonNull).collect(Collectors.toList());
            programList.addAll(newProgramStates);
            if (LOG_PROGRAM_STATE)
            {
                //programList.forEach(repository::logProgramState);
                repository.logRepository();
            }
            repository.setProgramList(programList);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void allStep()
    {
        executor = Executors.newFixedThreadPool(2);
        removeCompletedPrograms();
        List<ProgramState> programStates = repository.getProgramList();
        if (LOG_PROGRAM_STATE)
        {
            //programStates.forEach(repository::logProgramState);
            repository.logRepository();
        }
        while (programStates.size() > 0)
        {
            GarbageCollector.collectGarbage(programStates);
            oneStepForAllPrograms();
            removeCompletedPrograms();
            programStates = repository.getProgramList();
        }
        executor.shutdownNow();
    }

    private void removeCompletedPrograms()
    {
        List<ProgramState> programList = repository.getProgramList();
        repository.setProgramList(
                programList.stream().
                        filter(ProgramState::isNotCompleted).
                        collect(Collectors.toList())
        );
    }
}
