package view;

import controller.Controller;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

import static view.ProgramUtils.generatePrograms;

public class Main
{
    public static void main(String[] args)
    {
        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("\n\u2022 Number of program: ");
            int programNumber = scanner.nextInt();
            if (programNumber == -1) return;
            System.out.println();
            Statement program = generatePrograms().get(programNumber);
            ProgramState programState = new ProgramState(program);
            repository.setCurrentProgram(programState);
            controller.allStep();
        }
    }
}
