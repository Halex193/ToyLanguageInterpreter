package view.ui;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.programstate.ProgramState;
import model.statements.Statement;
import model.values.IntValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import view.GraphicalInterface;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProgramController
{
    public final String logFilePath = "logs/program.log";
    @FXML private Label programStateNumber;

    @FXML private ListView<Integer> programStateList;

    @FXML private ListView<Statement> executionStack;

    @FXML private ListView<Value> outputList;

    @FXML private TableView<Map.Entry<String, Value>> symbolTable;

    @FXML private TableView<Map.Entry<Integer, Value>> heapTable;

    @FXML private ListView<String> fileList;


    private Controller controller;
    public IRepository repository;

    @FXML
    void allSteps(ActionEvent event)
    {
        controller.allStep();
        populate();
    }

    @FXML
    void oneStep(ActionEvent event)
    {
        controller.oneStep();
        populate();
    }

    @FXML
    void openLog(ActionEvent event)
    {
        try
        {
            Desktop.getDesktop().open(new File(logFilePath));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void initialize(Statement statement)
    {
        repository = new Repository(new ProgramState(statement), logFilePath);
        controller = new Controller(repository);
        initializeProgramStateList();
        initializeSymbolTable();
        initializeHeapTable();
        populate();
    }

    private void populate()
    {
        List<ProgramState> programList = repository.getProgramList();
        setProgramStateCount(programList.size());
        programStateList.getItems().setAll(programList.stream().map(ProgramState::getId).collect(Collectors.toList()));

        if (programList.isEmpty())
        {
            programStateList.getItems().setAll();
            executionStack.getItems().setAll();
            symbolTable.getItems().setAll(new ArrayList<>(0));
            return;
        }
        programStateList.getSelectionModel().select(0);
        populateNonVolatile(programList.get(0));
    }

    private void setProgramStateCount(int count)
    {
        programStateNumber.setText(String.valueOf(count));
    }

    private void initializeProgramStateList()
    {
        programStateList.getSelectionModel().selectedItemProperty().addListener(this::populateVolatile);
    }

    private void initializeSymbolTable()
    {
        TableColumn<Map.Entry<String, Value>, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(tableData -> new SimpleStringProperty(tableData.getValue().getKey()));
        symbolTable.getColumns().add(nameColumn);

        TableColumn<Map.Entry<String, Value>, Value> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(tableData -> new SimpleObjectProperty<>(tableData.getValue().getValue()));
        symbolTable.getColumns().add(valueColumn);
    }

    private void initializeHeapTable()
    {
        TableColumn<Map.Entry<Integer, Value>, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(tableData -> new SimpleObjectProperty<>(tableData.getValue().getKey()));
        heapTable.getColumns().add(addressColumn);

        TableColumn<Map.Entry<Integer, Value>, Value> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(tableData -> new SimpleObjectProperty<>(tableData.getValue().getValue()));
        heapTable.getColumns().add(valueColumn);
    }

    private void populateVolatile(ObservableValue<? extends Integer> observable, Integer oldId, Integer newId)
    {
        Optional<ProgramState> anyState = repository.getProgramList().stream().filter((state) -> state.getId() == newId).findAny();
        if(anyState.isEmpty()) return;

        ProgramState programState = anyState.get();
        symbolTable.getItems().setAll(programState.getSymbolTable().getMap().entrySet());
        executionStack.getItems().setAll(programState.getExecutionStack().asList());
    }

    private void populateNonVolatile(ProgramState programState)
    {
        heapTable.getItems().setAll(programState.getHeap().getMap().entrySet());
        fileList.getItems().setAll(programState.getFileTable().getMap().keySet());
        outputList.getItems().setAll(programState.getProgramOutput().asList());
    }

}
