package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class MainScreenController {

    @FXML
    private HBox dynamicButtonsPane;

    @FXML
    private Button buttonProjects;

    @FXML
    private Button buttonCalendar;

    @FXML
    private Button buttonLogs;

    @FXML
    private BorderPane root;

    public void initialize()
    {
    	buttonProjects.setFont(new Font("SimSun", 12));
    	buttonCalendar.setFont(new Font("SimSun", 12));
    	buttonLogs.setFont(new Font("SimSun", 12));
    	buttonProjects.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
    	buttonCalendar.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
    	buttonLogs.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
    	root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    @FXML
    void ClickProjects(ActionEvent event)
    {
    	Data.main.sceneSetter(Data.main.sceneProjectsScreenController);
    	if (!Data.main.timerFlaga) Data.main.timerStart();
    }

    @FXML
    void clickLogs(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneLogs);
    	if (!Data.main.timerFlaga) Data.main.timerStart();
    }

    @FXML
    void clickCalendar(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneTime);
    	Data.timeAxis.calendarEntry = true;
    	if(Data.timeAxis.listLayout.getItems().isEmpty())	Data.timeAxis.listLayout.getItems().add("Choose date...");
    	Data.timeAxis.labelsSet(""+Data.timeAxis.currentWeekNumber);
    	if (!Data.main.timerFlaga) Data.main.timerStart();
    }
}