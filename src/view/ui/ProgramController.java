package view.ui;

import controller.Controller;
import exceptions.ProgramException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.programstate.ProgramState;
import model.statements.Statement;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import utils.ProgramUtils;
import view.GraphicalInterface;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProgramController
{
    public final String logFilePath = "logs/program.log";
    @FXML
    private Label programStateNumber;

    @FXML
    private ListView<Integer> programStateList;

    @FXML
    private ListView<Statement> executionStack;

    @FXML
    private ListView<Value> outputList;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTable;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<DTO> bonusList;

    @FXML
    private TableView<DTO> bonusTable;


    private Controller controller;
    public IRepository repository;
    private GraphicalInterface graphicalInterface;

    @FXML
    void allSteps(ActionEvent event)
    {
        Controller.ExecutionInformation information = controller.allStep();
        alertExceptions(information.getExceptions());
        populate(information.getLastState());
    }

    private void alertExceptions(Set<ProgramException> exceptions)
    {
        if(exceptions == null) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Thread execution halted!");
        exceptions.forEach(exception ->
        {
            alert.setContentText(String.format("Thread with id %d finished with exception: %s", exception.getProgramStateId(), exception.toString()));
            alert.showAndWait();
        });
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

    }

    @FXML
    void oneStep(ActionEvent event)
    {
        Controller.ExecutionInformation information = controller.oneStep();
        alertExceptions(information.getExceptions());
        populate(information.getLastState());
    }

    @FXML
    void openLog(ActionEvent event)
    {
        try
        {
            File file = new File(logFilePath);
            if (!file.exists()) return;
            Desktop.getDesktop().open(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event)
    {
        graphicalInterface.goToMenu();
    }

    public void initialize(Statement statement)
    {
        ProgramUtils.cleanLogDirectory();
        repository = new Repository(new ProgramState(statement), logFilePath);
        controller = new Controller(repository);
        initializeProgramStateList();
        initializeSymbolTable();
        initializeHeapTable();
        initializeBonusTable();
        populate(repository.getProgramList().get(0));
    }

    private void populate(ProgramState lastState)
    {
        List<ProgramState> programList = repository.getProgramList();
        setProgramStateCount(programList.size());
        programStateList.getItems().setAll(programList.stream().map(ProgramState::getId).collect(Collectors.toList()));

        if (programList.isEmpty())
        {
            programStateList.getItems().setAll();
            executionStack.getItems().setAll();
            symbolTable.getItems().setAll(new ArrayList<>(0));
            if (lastState != null)
            {
                populateNonVolatile(lastState);
            }
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

    static class DTO
    {
        //TODO
        int key;
        String value;

        //TODO
    }

    private void initializeBonusTable()
    {
        //TODO
        addColumn("Key", bonusTable, dto -> dto.key);
        addColumn("Value", bonusTable, dto -> dto.value);
    }

    private <DT, T> void addColumn(String name, TableView<DT> tableView, Function<DT, T> function)
    {
        TableColumn<DT, T> column = new TableColumn<>(name);
        column.setCellValueFactory(tableData -> new SimpleObjectProperty<>(function.apply(tableData.getValue())));
        tableView.getColumns().add(column);
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
        if (newId == null) return;
        Optional<ProgramState> anyState = repository.getProgramList().stream().filter((state) -> state.getId() == newId).findAny();
        if (anyState.isEmpty()) return;

        ProgramState programState = anyState.get();
        symbolTable.getItems().setAll(programState.getSymbolTable().getMap().entrySet());
        executionStack.getItems().setAll(programState.getExecutionStack().asList());
    }

    private void populateNonVolatile(ProgramState programState)
    {
        heapTable.getItems().setAll(programState.getHeap().getMap().entrySet());
        fileList.getItems().setAll(programState.getFileTable().getMap().keySet());
        outputList.getItems().setAll(programState.getProgramOutput().asList());
        //TODO
       // List<DTO> dtos = .stream().map(e -> new DTO()).collect(Collectors.toList());
       // bonusList.getItems().setAll(dtos);
       // bonusTable.getItems().setAll(dtos);
    }

    public void setApplication(GraphicalInterface graphicalInterface)
    {
        this.graphicalInterface = graphicalInterface;
    }
}
