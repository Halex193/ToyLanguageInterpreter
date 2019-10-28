package controller;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

public class Controller
{
    private static final boolean DISPLAY = true;

    private IRepository repository;

    public Controller(IRepository repository)
    {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState)
    {
        if (programState.isFinished())
            throw new ProgramFinishedException();
        Statement currentStatement = programState.getExecutionStack().pop();
        try
        {
            ProgramState newProgramState = currentStatement.execute(programState);
            if (DISPLAY)
            {
                System.out.format(
                        "Executed %s: %s\n",
                        currentStatement.getClass().getSimpleName(),
                        currentStatement.toString()
                );

                System.out.print(programState);
                System.out.println("------------------------");
            }
            return newProgramState;
        }
        catch (ProgramException exception)
        {
            if (DISPLAY)
            {
                System.err.println("Program finished with error: \n" + exception);
            }
            return null;
        }
    }

    public void allStep()
    {
        ProgramState programState = repository.getCurrentProgram();
        if (DISPLAY)
        {
            System.out.format("Executing program : %s\n", programState.getProgram());
            System.out.print(programState);
            System.out.println("------------------------");
        }
        while (!programState.isFinished())
        {
            if (oneStep(programState) == null)
                return;
        }

        System.out.println("Program execution finished");
    }
}
