package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.statements.Statement;
import view.ui.MenuController;
import view.ui.ProgramController;

import java.io.IOException;

public class GraphicalInterface extends Application
{
    Stage stage;
    public Parent menuRoot;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/menu.fxml"));
        menuRoot = loader.load();
        MenuController mainController = loader.getController();
        mainController.setApplication(this);

        primaryStage.setTitle("ToyLanguageInterpreter");
        primaryStage.setScene(new Scene(menuRoot, 800, 400));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    public void runProgram(Statement statement)
    {
        if (statement == null) return;

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/program.fxml"));
            Parent programRoot = loader.load();
            ProgramController programController = loader.getController();
            programController.initialize(statement);
            stage.getScene().setRoot(programRoot);
            stage.setOnCloseRequest(windowEvent ->
            {
                stage.getScene().setRoot(menuRoot);
                stage.setOnCloseRequest(windowEvent1 -> stage.close());
                windowEvent.consume();
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
