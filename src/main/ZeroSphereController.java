package main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ZeroSphereController {

    @FXML
    private Button buttonZero;   

    @FXML
    void buttonZeroClick(ActionEvent event) throws IOException 
    {    	
    	ZeroSphere.goMainScreen();
    }
    public void initialize()
    {
    	buttonZero.setFont(new Font("SimSun", 12));
    	buttonZero.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
    	buttonZero.setPrefSize(200, 100);
    }
}