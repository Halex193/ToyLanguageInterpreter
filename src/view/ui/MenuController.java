package view.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.statements.Statement;
import utils.ProgramUtils;
import view.GraphicalInterface;


public class MenuController
{
    @FXML
    private ListView<Statement> programList;

    @FXML
    private Button runButton;

    private GraphicalInterface graphicalInterface;

    @FXML
    void onRunButtonClicked(ActionEvent event)
    {
        runProgram();
    }

    @FXML
    void onListItemClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            runProgram();
        }
    }


    private void runProgram()
    {
        graphicalInterface.runProgram(programList.getSelectionModel().getSelectedItem());
    }

    public void setApplication(GraphicalInterface graphicalInterface)
    {
        this.graphicalInterface = graphicalInterface;
        ObservableList<Statement> statements = FXCollections.observableArrayList();
        statements.addAll(ProgramUtils.generatePrograms());
        programList.setItems(statements);
    }
}

