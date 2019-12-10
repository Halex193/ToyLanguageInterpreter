package controller;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.ApplicationDictionary;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    public ProgramState allStep()
    {
        ProgramState state = null;
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
            state = repository.getProgramList().get(0);
            removeCompletedPrograms();
            programStates = repository.getProgramList();
        }
        executor.shutdownNow();
        return state;
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

    public void execute()
    {
        Statement program = repository.getProgramList().get(0).getProgram();
        try
        {
            program.typeCheck(new ApplicationDictionary<>());
            allStep();
        }
        catch (TypeMismatchException e)
        {
            System.err.println(String.format("Type checking failed: %s", e.getMessage()));
        }
    }

    public ProgramState oneStep()
    {
        ProgramState state = null;
        executor = Executors.newFixedThreadPool(2);
        removeCompletedPrograms();
        List<ProgramState> programStates = repository.getProgramList();
        if (LOG_PROGRAM_STATE)
        {
            repository.logRepository();
        }
        if(!programStates.isEmpty())
        {
            GarbageCollector.collectGarbage(programStates);
            oneStepForAllPrograms();
            state = repository.getProgramList().get(0);
            removeCompletedPrograms();
        }
        executor.shutdownNow();
        return state;
    }
}
