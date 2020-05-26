package extras;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Data;


public class MyDialogController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button buttonOk;   

    @FXML
    private Label labelName;
    
    public Stage window;
    
    @FXML 
    void initialize()
    {   	
    	Data.main.myDialog = this;
    }   

    @FXML
    void clickButtonOk(ActionEvent event) 
    {
    	exitAlert();
    }

    public void setDialog(String txt, Stage window)
    {
    	labelName.setText(txt);
    	this.window = window;
    	
    	window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				exitAlert();
			}
		});
    }
    
    private void exitAlert()
    {
    	if (Data.main.kolejkaAlertow.peek() == null)
    	{
    		Data.dynamicScreens.infoIcon.setVisible(false);
    		Data.dynamicScreens.infoIcon.setDisable(true);
    	}
    	window.close();
    	Data.main.dialogIsShowed = false;
    }
}
