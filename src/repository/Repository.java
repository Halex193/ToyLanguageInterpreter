package repository;

import exceptions.ProgramException;
import model.programstate.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository implements IRepository
{
    private String logFilePath;
    private List<ProgramState> programStates;

    public Repository(ProgramState programState, String logFilePath)
    {
        this.logFilePath = logFilePath;
        programStates = new ArrayList<>(1);
        programStates.add(programState);
    }

    @Override
    public void logProgramState(ProgramState programState)
    {
        if (logFilePath == null)
        {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(logFilePath, true))
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            printWriter.println(programState.toString() + "\n-------------");
            printWriter.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void logRepository()
    {
        if (logFilePath == null)
        {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(logFilePath, true))
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            programStates.forEach(programState ->
            {
                printWriter.println(
                        String.format("Program state id: %d\nExecution Stack:\n%s\nSymbol Table:\n%s\n",
                                programState.getId(),
                                programState.getExecutionStack(),
                                programState.getSymbolTable()
                        )
                );
            });
            Optional<ProgramState> anyProgramState = programStates.stream().findAny();
            if (anyProgramState.isEmpty())
            {
                return;
            }
            ProgramState programState = anyProgramState.get();
            String common = String.format("All Program states\nProgram output:\n%s\nFile Table:\n%s\nHeap:\n%s\n-------------------",
                    programState.getProgramOutput(),
                    programState.getFileTable(),
                    programState.getHeap()
            );
            printWriter.println(common);
            printWriter.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void logException(ProgramException exception)
    {
        if (logFilePath == null)
        {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(logFilePath, true))
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            printWriter.println(String.format("Thread with id %d finished with exception: %s\n-------------", exception.getProgramStateId(), exception.toString()));
            printWriter.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProgramState> getProgramList()
    {
        return programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates)
    {
        this.programStates = programStates;
    }
}
