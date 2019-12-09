package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ui.MainController;

public class GraphicalInterface extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
        MainController mainController = loader.getController();
        Parent root = loader.load();

        primaryStage.setTitle("ToyLanguageInterpreter");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
