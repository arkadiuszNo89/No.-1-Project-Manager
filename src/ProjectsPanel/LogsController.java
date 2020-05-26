package ProjectsPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.Data;


public class LogsController {
	
	public static TextArea textLogsTemp;

    @FXML
    private TextArea textLogs;
    
    @FXML
    void initialize()
    {
    	textLogsTemp = this.textLogs;
    	
    	textLogs.setEditable(false);
    } 

    @FXML
    void clickClear(ActionEvent event) 
    {
    	textLogs.clear();
    }

    @FXML
    void clickMain(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneMainScreen);
    }
    
    @FXML
    void clickCalendar(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneTime);
    	Data.timeAxis.calendarEntry = true;
    	if(Data.timeAxis.listLayout.getItems().isEmpty())	Data.timeAxis.listLayout.getItems().add("Choose date...");
    	Data.timeAxis.labelsSet(""+Data.timeAxis.currentWeekNumber);
    }

    @FXML
    void clickProjects(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneProjectsScreenController);
    }
}