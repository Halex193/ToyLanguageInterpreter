package view;

import controller.Controller;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;
import repository.Repository;

import static view.ProgramUtils.generatePrograms;

public class Main
{
    public static void main(String[] args)
    {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        Statement program = generatePrograms().get(0);
        ProgramState programState = new ProgramState(program);
        repository.setCurrentProgram(programState);
        controller.allStep();
    }
}
