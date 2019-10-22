package controller;

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
        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty())
            throw new ProgramFinishedException();
        Statement currentStatement = executionStack.pop();
        if (DISPLAY)
        {
            System.out.format(
                    "Executed %s: %s\n",
                    currentStatement.getClass().getSimpleName(),
                    currentStatement.toString()
            );
        }
        return currentStatement.execute(programState);
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
            oneStep(programState);
            if (DISPLAY)
            {
                System.out.println(programState);
                System.out.println("------------------------");
            }
        }
    }
}
