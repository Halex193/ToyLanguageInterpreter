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
        programStates.add(programState);
    }

    @Override
    public void logProgramState(ProgramState programState)
    {
        if (logFilePath == null)
        {
            return;
        }
        try(FileWriter fileWriter = new FileWriter(logFilePath, true))
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            printWriter.println(programState.toString() + "\n-------------");
            printWriter.flush();
        }
        catch (IOException e)
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
