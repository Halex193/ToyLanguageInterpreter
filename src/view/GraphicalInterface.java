package view;

import exceptions.TypeMismatchException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.programstate.ApplicationDictionary;
import model.statements.Statement;
import view.ui.MenuController;
import view.ui.ProgramController;

import java.io.IOException;

public class GraphicalInterface extends Application
{
    private Stage stage;
    private Parent menuRoot;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/menu.fxml"));
        menuRoot = loader.load();
        MenuController mainController = loader.getController();
        mainController.setApplication(this);

        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.setScene(new Scene(menuRoot, 800, 400));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    public void goToMenu()
    {
        stage.getScene().setRoot(menuRoot);
    }
    public void runProgram(Statement statement)
    {
        if (statement == null) return;
        try
        {
            statement.typeCheck(new ApplicationDictionary<>());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/program.fxml"));
            Parent programRoot = loader.load();
            ProgramController programController = loader.getController();
            programController.setApplication(this);
            programController.initialize(statement);
            stage.getScene().setRoot(programRoot);
        }
        catch (TypeMismatchException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Type checking failed!");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
