package main;

import ProjectsPanel.*;
import extras.MyDialogController;
import extras.SortByNames;
import extras.SortByTime;
import extras.TimeTable;
import extras.TimerAct;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main
{
	private Stage stage;
	public Scene sceneMainScreen, sceneAddProject, sceneProjectsScreenController, sceneTime, sceneLogs;
	public BorderPane layoutIcons, layoutList;
	
	public List <Project> projectsList, projectsListNames, projectsListTime;	
	public List <String> projectsListString, projectsListNamesString, projectsListTimeString;
	public FlowPane dynamicButtonsPaneNewest, dynamicButtonsPaneNames, dynamicButtonsPaneTime;
	public ListView<String> projectsListLayout, projectsListLayoutNames, projectsListLayoutTime;
	public ChoiceBox<String> choiceBoxScreenController;	
	public BorderPane projectsListView;
	public Project actualProject;	
	public Icons iconsPaneNewest, iconsPaneNames, iconsPaneTime;
	public TimeTable year2020;
	private Timer timer;
	public boolean timerFlaga, dialogIsShowed, flagaEditProject;
	public MyDialogController myDialog;
	public Queue <String> kolejkaAlertow;	
	public String saveLoadPath = null;	
	public Map <String, Project> mapaNazw;
	public Data data;
	
	public Main(Stage primaryStage) throws IOException
	{
		stage = primaryStage;
		
		sceneMainScreen = sceneCreator("MainScreenView.fxml");
		sceneAddProject = sceneCreator("/ProjectsPanel/AddProjectView.fxml");
		sceneProjectsScreenController = sceneCreator("/ProjectsPanel/ProjectsScreenView.fxml");
		sceneTime = sceneCreator("/extras/TimeAxisView.fxml");
		sceneLogs = sceneCreator("/ProjectsPanel/LogsView.fxml");
		layoutList = (BorderPane)layoutCreator("/ProjectsPanel/ListView.fxml");
		
		projectsList = new ArrayList<Project>();
		projectsListNames = new ArrayList<Project>();
		projectsListTime = new ArrayList<Project>();
		projectsListString = new ArrayList<String>();
		projectsListNamesString = new ArrayList<String>();
		projectsListTimeString = new ArrayList<String>();
		mapaNazw = new HashMap <>();
		
		iconsPaneNewest = new Icons(); 		
		dynamicButtonsPaneNewest = Icons.dynamicButtonsPaneTemp;
		iconsPaneNames = new Icons();
		dynamicButtonsPaneNames = Icons.dynamicButtonsPaneTemp; 
		iconsPaneTime = new Icons();
		dynamicButtonsPaneTime = Icons.dynamicButtonsPaneTemp;
		
    	year2020 = new TimeTable("2020"); 	
    	timerFlaga = false;	
    	kolejkaAlertow = new ArrayDeque <String> ();
    	dialogIsShowed = false; 	
    	flagaEditProject = false;
    	
    	stage.setOnCloseRequest(this::closeEvent);
	}
	
	public Scene mainSceneGetter()
	{
		return sceneMainScreen;
	}
	public Scene sceneCreator(String fxmlAdress) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlAdress));
		return new Scene(loader.load()); 
	}
	public Pane layoutCreator(String fxmlAdress) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlAdress));
		return loader.load();
	}
	public void sceneSetter(Scene scene)
	{
		this.stage.setScene(scene);
	}
	public void addToLists (Project project)
	{
		projectsList.add(project);
		projectsListNames.add(project);
		projectsListTime.add(project);
		
		Collections.sort(projectsListNames, new SortByNames());
		Collections.sort(projectsListTime, new SortByTime());
		
		projectsListString.add(project.getText());
		projectsListNamesString.clear();
		for (Project projectItem : projectsListNames)	projectsListNamesString.add(projectItem.getText());
		projectsListTimeString.clear();
		for (Project projectItem : projectsListTime)	projectsListTimeString.add(projectItem.getText());
		
		mapaNazw.put(project.getText(), project);
	}
	public void removeFromLists (Project project)
	{
		projectsList.remove(project);
		projectsListNames.remove(project);
		projectsListTime.remove(project);		
		
		projectsListString.remove(project.getText());
		projectsListNamesString.remove(project.getText());
		projectsListTimeString.remove(project.getText());
		
		mapaNazw.remove(project.getText());
		System.out.println(mapaNazw);
	}
	public void timerStart()
	{
    	timer = new Timer();									
    	timer.scheduleAtFixedRate(new TimerAct(), 0, 10000);
    	timerFlaga = true;
	}

	private void closeEvent(WindowEvent event)
	{				
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Quit application");
		alert.setContentText("Do you want do quit?");		
		alert.initOwner(stage.getOwner()); 			
		Optional<ButtonType> result = alert.showAndWait(); 	
		
		if (result.isPresent())
		{
			if (result.get().equals(ButtonType.CANCEL))
			{
				event.consume();
			}
			else if (result.get().equals(ButtonType.OK) && timerFlaga == true)
			{
				timer.cancel();
			}
		}	 
	}
	
	public void showMyDialog(String name) throws IOException
	{
		Stage myWindow = new Stage();
		BorderPane root;
		Scene alertScene;
		
		FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("/extras/MyDialogView.fxml"));
		root = loader1.load();
		alertScene = new Scene(root);
		
		myWindow.setScene(alertScene);
		myWindow.setTitle("Info");
		myWindow.show();
		dialogIsShowed = true;
		
		myDialog.setDialog(name, myWindow);
	}
	
	public void removeProject (Project project)
	{		
		Data.timeAxis.onOffBlackBoardViewer(false);
		this.year2020.input(project, true); 
		this.removeFromLists(project);
		Data.dynamicScreens.setDynamicButtonsScreen();	
		Data.timeAxis.labelsSet(Data.timeAxis.spinnerWeekNr.getValue());
		if (Data.timeAxis.calendarEntry) Data.timeAxis.handle_clickDateLabel(Data.timeAxis.actualLabel);
		Project.qty--;
	}
}