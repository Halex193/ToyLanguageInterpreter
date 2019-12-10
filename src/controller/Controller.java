package controller;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.programstate.ApplicationDictionary;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

import java.util.*;
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

    public Set<ProgramException> oneStepForAllPrograms()
    {
        List<ProgramState> programList = repository.getProgramList();
        List<Callable<ProgramState>> callables = programList.stream().
                map(programState -> (Callable<ProgramState>) programState::oneStep).
                collect(Collectors.toList());
        Set<ProgramException> exceptions = new HashSet<>();
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
                                exceptions.add(exception);
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
        return exceptions;
    }

    public ExecutionInformation allStep()
    {
        executor = Executors.newFixedThreadPool(2);
        removeCompletedPrograms();
        List<ProgramState> programStates = repository.getProgramList();
        if (LOG_PROGRAM_STATE)
        {
            //programStates.forEach(repository::logProgramState);
            repository.logRepository();
        }

        ProgramState state = null;
        Set<ProgramException> exceptions = null;
        while (programStates.size() > 0)
        {
            GarbageCollector.collectGarbage(programStates);
            exceptions = oneStepForAllPrograms();
            state = repository.getProgramList().get(0);
            removeCompletedPrograms();
            programStates = repository.getProgramList();
        }
        executor.shutdownNow();
        return new ExecutionInformation(state, exceptions);
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

    public ExecutionInformation oneStep()
    {
        executor = Executors.newFixedThreadPool(2);
        removeCompletedPrograms();
        List<ProgramState> programStates = repository.getProgramList();
        ProgramState state = null;
        Set<ProgramException> exceptions = null;
        if(!programStates.isEmpty())
        {
            GarbageCollector.collectGarbage(programStates);
            exceptions = oneStepForAllPrograms();
            state = repository.getProgramList().get(0);
            removeCompletedPrograms();
        }
        executor.shutdownNow();
        return new ExecutionInformation(state, exceptions);
    }

    public static class ExecutionInformation
    {
        private ProgramState lastState;
        private Set<ProgramException> exceptions;

        public ExecutionInformation(ProgramState lastState, Set<ProgramException> exceptions)
        {
            this.lastState = lastState;
            this.exceptions = exceptions;
        }

        public ProgramState getLastState()
        {
            return lastState;
        }

        public Set<ProgramException> getExceptions()
        {
            return exceptions;
        }
    }
}
