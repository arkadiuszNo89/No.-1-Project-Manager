package main;

import ProjectsPanel.*;
import extras.TimeAxisController;
import javafx.scene.control.TextArea;

public class Data 
{
	public static Main main;
	public static AddProjectController addProjectController;
	public static TimeAxisController timeAxis;
	public static ProjectsScreenController dynamicScreens;
	public static ListController listController; 
	public static TextArea textLogs;
	
	
	public Data(Main main)
	{
		Data.main = main;
		TimeAxisController.main = main;
		addProjectController = AddProjectController.tempAddProjectController;
		dynamicScreens = ProjectsScreenController.dynamicScreens;
		timeAxis = TimeAxisController.timeAxis;
		listController = ListController.listControllerTemp;
		textLogs = LogsController.textLogsTemp;
		main.projectsListLayout = ListController.projectsListLayoutTemp;
		main.projectsListView = ListController.projectsListViewTemp;
		
		timeAxis.labelsSet(timeAxis.spinnerWeekNr.getValue());
	}
}
