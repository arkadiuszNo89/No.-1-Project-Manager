package ProjectsPanel;

import java.io.IOException;
import extras.myLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.Data;


public class ProjectsScreenController 
{
	
    @FXML
    public BorderPane rootPane;

    @FXML
    public Button AddProjectButton;
    
    @FXML
    private Button backToMenuButton;
    
    @FXML
    private Button changeLayoutButton;

    @FXML
    private ChoiceBox<String> choiceBoxLayoutChange;
    
    @FXML
    private ChoiceBox<String> choiceBoxSort;
    
    @FXML
    private Button functionButton1;

    @FXML
    private Button functionButton2;

    @FXML
    private Button functionButton3;
    
    @FXML
    private ImageView iconSave;

    @FXML
    private ImageView iconFunc;
    
    @FXML
    public ImageView infoIcon;
    
    public static BorderPane projectsScreenControllerTemp;
    public static ProjectsScreenController dynamicScreens;
    
    private String buttonTxt1 = "F1";
    private String buttonTxt2 = "F2";
    private String buttonTxt3 = "F3";
    
    private EventHandler <ActionEvent> goCalendar, goLogs, save, saveAs, load;
    public EventHandler <ActionEvent> deleteAlerts;

    @FXML
    void AddProjectButtonClick(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneAddProject);
    }
    
    @FXML
    void backToMenuButtonClick(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneMainScreen);
    }
    
    @FXML
    void changeLayoutButtonClick(ActionEvent event) 
    {	
    	setDynamicButtonsScreen();
    }
    
    @FXML
    void initialize() 
    {
    	choiceBoxLayoutChange.getItems().addAll("Icons", "List");
    	choiceBoxLayoutChange.setValue("Icons");
    	choiceBoxSort.getItems().addAll("Newest", "Name", "Time");
    	choiceBoxSort.setValue("Newest");
    	
    	projectsScreenControllerTemp = rootPane;
    	dynamicScreens = this;
    	rootPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    	
    	changeFuncButtons (false, buttonTxt1, buttonTxt2, buttonTxt3);
    	iconSave.setOpacity(0.3);
    	iconFunc.setOpacity(0.3);
    	infoIcon.setVisible(false);
    	infoIcon.setDisable(true);

    	createActions();	
    	functionButton1.setOnAction(goCalendar);
    	functionButton2.setOnAction(goLogs);
    	functionButton3.setOnAction(deleteAlerts);
    }
    
    @FXML
    void clickIconSave (MouseEvent event) 
    {
    	if (iconSave.getOpacity() < 1)
    	{
    		iconSave.setOpacity(1);
    		iconFunc.setOpacity(0.3);
    		changeFuncButtons(true, "SAVE", "SAVE AS", "LOAD");
    		setActionForButtons(save, saveAs, load);
    	}
    	else
    	{
    		iconSave.setOpacity(0.3);
    		changeFuncButtons(false);
    	}
    }
    
    @FXML
    void ClickIconFunc (MouseEvent event) 
    {
    	if (iconFunc.getOpacity() < 1)
    	{
    		iconFunc.setOpacity(1);
    		iconSave.setOpacity(0.3);
    		changeFuncButtons(true, "C", "L", "Clear");
    		setActionForButtons(goCalendar, goLogs, deleteAlerts);
    	}
    	else
    	{
    		iconFunc.setOpacity(0.3);
    		changeFuncButtons(false);
    	}
    }
    
    private void changeFuncButtons (boolean visable, String txt1, String txt2, String txt3)
    {
    	functionButton1.setVisible(visable);
    	functionButton2.setVisible(visable);
    	functionButton3.setVisible(visable);
    	
    	functionButton1.setText(txt1);
    	functionButton2.setText(txt2);
    	functionButton3.setText(txt3);
    }
    private void changeFuncButtons (boolean visable)
    {
    	functionButton1.setVisible(visable);
    	functionButton2.setVisible(visable);
    	functionButton3.setVisible(visable);
    }
    private void setActionForButtons (EventHandler <ActionEvent> A, EventHandler <ActionEvent> B, EventHandler <ActionEvent> C)
    {
    	functionButton1.setOnAction(A);
    	functionButton2.setOnAction(B);
    	functionButton3.setOnAction(C);
    }
    public void setDynamicButtonsScreen()
    {   	
    	if (choiceBoxLayoutChange.getValue().toString() == "Icons" && !BlackBoard.editMode)
    	{    		
        	if (choiceBoxSort.getValue().toString() == "Newest")
        	{
        		Data.main.dynamicButtonsPaneNewest.getChildren().clear(); 
        		Data.main.dynamicButtonsPaneNewest.getChildren().addAll(Data.main.projectsList);
        		rootPane.setCenter(Data.main.iconsPaneNewest);
        	}
        	else if (choiceBoxSort.getValue().toString() == "Name")
        	{
        		Data.main.dynamicButtonsPaneNames.getChildren().clear();
        		Data.main.dynamicButtonsPaneNames.getChildren().addAll(Data.main.projectsListNames);	   
        		rootPane.setCenter(Data.main.iconsPaneNames);
        	}
        	else if (choiceBoxSort.getValue().toString() == "Time")
        	{
        		Data.main.dynamicButtonsPaneTime.getChildren().clear();
        		Data.main.dynamicButtonsPaneTime.getChildren().addAll(Data.main.projectsListTime); 
        		rootPane.setCenter(Data.main.iconsPaneTime);
        	}
    	}
    	else if (choiceBoxLayoutChange.getValue().toString() == "List" && !BlackBoard.editMode)
    	{
        	if (choiceBoxSort.getValue().toString() == "Newest")
        	{
        		Data.main.projectsListLayout.getItems().clear();
        		Data.main.projectsListLayout.getItems().addAll(Data.main.projectsListString);
        		rootPane.setCenter(Data.main.layoutList);
        	}
        	else if (choiceBoxSort.getValue().toString() == "Name")
        	{
        		Data.main.projectsListLayout.getItems().clear();
        		Data.main.projectsListLayout.getItems().addAll(Data.main.projectsListNamesString);
        		rootPane.setCenter(Data.main.layoutList);
        	}
        	else if (choiceBoxSort.getValue().toString() == "Time")
        	{
        		Data.main.projectsListLayout.getItems().clear();
        		Data.main.projectsListLayout.getItems().addAll(Data.main.projectsListTimeString);
        		rootPane.setCenter(Data.main.layoutList);
        	}
    	}
    }
    
    @FXML
    void clickInfoIcon(MouseEvent event) throws IOException 
    {
    	if (Data.main.kolejkaAlertow.peek() != null)
    	{
    		Data.main.showMyDialog(Data.main.kolejkaAlertow.poll());
    		if (Data.main.kolejkaAlertow.peek() == null)
        	{
            	infoIcon.setVisible(false);
            	infoIcon.setDisable(true); 
        	}
    	}
    }
    
    private void createActions()
    {
    	goCalendar = new EventHandler <ActionEvent> () 
    		{
			
				@Override
				public void handle(ActionEvent arg0) 
				{
					Data.main.sceneSetter(Data.main.sceneTime);
			    	Data.timeAxis.calendarEntry = true;
			    	if(Data.timeAxis.listLayout.getItems().isEmpty())	Data.timeAxis.listLayout.getItems().add("Choose date...");
			    	Data.timeAxis.labelsSet(""+Data.timeAxis.currentWeekNumber);
			    	if (!Data.main.timerFlaga) Data.main.timerStart();
				}
    		};
    	goLogs = new EventHandler <ActionEvent> () 
	    	{	
				@Override
				public void handle(ActionEvent arg0) 
				{
					Data.main.sceneSetter(Data.main.sceneLogs);
				}
			};
		deleteAlerts = new EventHandler <ActionEvent> ()
			{
				@Override
				public void handle (ActionEvent arg)
				{
			    	if (Data.main.kolejkaAlertow.peek() != null)
			    	{
			    		Data.main.kolejkaAlertow.clear();
		            	infoIcon.setVisible(false);
		            	infoIcon.setDisable(true); 
			    	}
				}
			};
	    save = new EventHandler <ActionEvent> () 
	    {	
			@Override
			public void handle(ActionEvent arg0) 
			{
				try 
				{
					new myLoader("save");
					Data.textLogs.appendText("Zapis danych.\n");
				} 
				catch (IOException e) 
				{
					Data.textLogs.appendText("Zapisywanie nie powiod³o siê.\n");
				}
			}
	    };
	    saveAs = new EventHandler <ActionEvent> () 
	    {	
			@Override
			public void handle(ActionEvent arg0) 
			{
				try 
				{
					new myLoader("save as");
					Data.textLogs.appendText("Zapisano jako.\n");
				} 
				catch (IOException e) 
				{
					Data.textLogs.appendText("Zapisywanie nie powiod³o siê.\n");
				}
			}
	    };
	    load = new EventHandler <ActionEvent> () 
	    {	
			@Override
			public void handle(ActionEvent arg0) 
			{
				try 
				{
					Data.textLogs.appendText("Za³adowano nowe dane:\n");
					new myLoader("load");
					Data.textLogs.appendText("Koniec ³adowania.\n");
				} 
				catch (IOException e) 
				{
					System.out.println("Load nie powiód³ siê");
				}
			}
	    };
    }
}




