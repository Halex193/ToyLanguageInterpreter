package controller;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

public class Controller
{
    private static final boolean DISPLAY = false;
    private static final boolean LOG_PROGRAM_STATE = true;

    private IRepository repository;

    public Controller(IRepository repository)
    {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws ProgramException
    {
        if (programState.isFinished())
            throw new ProgramFinishedException();
        Statement currentStatement = programState.getExecutionStack().pop();
        ProgramState newProgramState = currentStatement.execute(programState);
        if (DISPLAY)
        {
            System.out.format(
                    "Executed %s: %s\n",
                    currentStatement.getClass().getSimpleName(),
                    currentStatement.toString()
            );
        }
        if (LOG_PROGRAM_STATE)
        {
            repository.logProgramState();
        }
        return newProgramState;
    }

    public void allStep() throws ProgramException
    {
        ProgramState programState = repository.getCurrentProgram();
        if (DISPLAY)
        {
            System.out.format("Executing program : %s\n", programState.getProgram());
        }
        if (LOG_PROGRAM_STATE)
        {
            repository.logProgramState();
        }
        while (!programState.isFinished())
        {
            if (oneStep(programState) == null)
                return;
            GarbageCollector.collectGarbage(programState.getHeap(), programState.getSymbolTable());
        }
    }
}
