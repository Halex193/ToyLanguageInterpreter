package controller;

import exceptions.ProgramException;
import exceptions.ProgramFinishedException;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;

public class Controller
{
    public static final boolean DISPLAY = true;

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
        if (DISPLAY)
        {
            System.out.format(
                    "Executed %s: %s\n",
                    currentStatement.getClass().getSimpleName(),
                    currentStatement.toString()
            );
        }
        try
        {
            return currentStatement.execute(programState);
        }
        catch (ProgramException exception)
        {
            if(DISPLAY)
            {
                System.out.println("Program finished with error: \n" + exception);
            }
            return null;
        }
    }

    public void allStep()
    {
        ProgramState programState = repository.getCurrentProgram();
        if (DISPLAY)
        {
            System.out.format("Executing program : %s\n\n", programState.getProgram());
            System.out.println(programState);
            System.out.println("------------------------");
        }
        while (!programState.getExecutionStack().isEmpty())
        {
            if(oneStep(programState) == null)
                return;
            if (DISPLAY)
            {
                System.out.println(programState);
                System.out.println("------------------------");
            }
        }
    }
}
