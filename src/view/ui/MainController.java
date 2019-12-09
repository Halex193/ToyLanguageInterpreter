package view.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController
{

    @FXML
    private Button myButton;

    @FXML
    void onClicked(ActionEvent event)
    {
        myButton.setText("Test text");
    }

}
