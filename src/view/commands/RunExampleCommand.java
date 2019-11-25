package view.commands;

import controller.Controller;
import exceptions.ProgramException;

public class RunExampleCommand extends Command
{
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute()
    {
        controller.allStep();
        System.out.println("Program execution finished");
    }
}

