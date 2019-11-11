package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationList;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.statements.Statement;
import model.values.Value;

public class Repository implements IRepository
{
    private String logFilePath;
    private List<ProgramState> programStates;

    public Repository(ProgramState programState, String logFilePath)
    {
        this.logFilePath = logFilePath;
        programStates = new ArrayList<>(1);
        setCurrentProgram(programState);
    }

    @Override
    public ProgramState getCurrentProgram()
    {
        return programStates.get(0);
    }

    @Override
    public void setCurrentProgram(ProgramState programState)
    {
        if (programStates.isEmpty())
            programStates.add(programState);
        else
            programStates.set(0, programState);
    }

    @Override
    public ProgramState getProgramState(int number)
    {
        return programStates.get(number);
    }

    @Override
    public void logProgramState()
    {
        if (logFilePath == null)
        {
            return;
        }
        try(FileWriter fileWriter = new FileWriter(logFilePath, true))
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            ProgramState programState = getCurrentProgram();
            printWriter.println(programState.toString() + "\n-------------");
            printWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
