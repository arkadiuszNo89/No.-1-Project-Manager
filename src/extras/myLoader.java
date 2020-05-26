package extras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ProjectsPanel.BlackBoard;
import ProjectsPanel.Project;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import main.Data;


public class myLoader 
{
	FileChooser fileChooser;
	Scanner fileReader;
	File file=null;
	
	public myLoader (String mode) throws IOException
	{		
		switch(mode)
		{
			case "save":
				
				fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.txt", "*.txt"));
				
				if (Data.main.saveLoadPath != null)
				{
					file = new File(Data.main.saveLoadPath);
					saveMaker();
				}
				else 
				{
					file = fileChooser.showSaveDialog(null);
					saveMaker();
				}
				break;
				
			case "save as":

				fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.txt", "*.txt"));
				file = fileChooser.showSaveDialog(null);						
				saveMaker();
	
				break;
				
			case "load":
				
				fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.txt", "*.txt"));
				file = fileChooser.showOpenDialog(null);
				List <Project> tempList = new ArrayList <Project> ();

				if (file != null) 
				{
					try 
					{
						fileReader = new Scanner(new BufferedReader(new FileReader(file))); 

						if (fileReader.nextLine().equals("#PM save#"))
						{
			    	    	tempList.addAll(Data.main.projectsList);
			    	    	for (Project project : tempList) Data.main.removeProject(project);
			    	    	tempList.clear();
			    	    	Data.dynamicScreens.deleteAlerts.handle(null);	// w³¹czam handlera
			    	    	Project.qty = 1;
			    	    	Data.main.saveLoadPath = file.getPath();	    	    	
			    	    	String readed;
			    	    	
							while(fileReader.hasNext())
							{
								readed = fileReader.nextLine();
								
								if(readed.equals("#Project"))
								{	
									// Tworzê nowy projekt, dalej inicjujê dane theBigSetterem, który na zakoñczenie zwraca fa³sz.
									Project newProj = new Project(fileReader.nextLine());
									while (newProj.theBigSetter_13Steps(fileReader.nextLine()));
									
					    	    	Data.addProjectController.setOpacityByLevel(newProj, 
					    	    			Data.addProjectController.currentDateCheck(newProj.dateStartVal, 
					    	    					newProj.dateEndVal, newProj.startHour, newProj.endHour)); 
					    	    	
					    	    	archiveProtector(newProj);
					    	    	if (Data.main.year2020.input(newProj, false))	Data.main.addToLists(newProj); 	
								}
								else if(readed.contains("#TextArea"))
								{
									Project temp = Data.main.mapaNazw.get(readed.substring(19, readed.length()));
									temp.blackBoardReturn().addText.handle(null);
				
									while(true)
									{
										readed = fileReader.nextLine();
										if (readed.equals("#End")) break;
										((TextArea)BlackBoard.freshNode).appendText(readed+"\n");
									}
									((TextArea)BlackBoard.freshNode).selectRange(0, 0);
								}
								else if(readed.contains("#Label"))
								{
									Project temp = Data.main.mapaNazw.get(readed.substring(16, readed.length()));
									readed = fileReader.nextLine();
									temp.blackBoardReturn().textField.setText(readed);
									temp.blackBoardReturn().addLabel.handle(null);
									temp.blackBoardReturn().textField.clear();
								}
								else if(readed.contains("#Note"))
								{
									Data.timeAxis.notes.clear();
									while(true)
									{
										readed = fileReader.nextLine();
										if (readed.equals("#End")) break;
										Data.timeAxis.notes.appendText(readed+"\n");
									}
									Data.timeAxis.notes.selectRange(0, 0);
								}
							}
							Data.main.sceneSetter(Data.main.sceneProjectsScreenController);
	    	    	    	Data.dynamicScreens.setDynamicButtonsScreen(); 
						}
					} 
					finally
					{
						if (fileReader != null) fileReader.close();
					}	 
					break;
				}
				
			default:
				Data.textLogs.appendText("Niedokoñczone ³adowanie projektów.\n");
				break;				
		}
	}
	
	private void saveMaker() throws IOException
	{
		if (file != null)
		{
			Data.main.saveLoadPath = file.getPath();	
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("#PM save#\n");

			for (Project project : Data.main.projectsList)
			{
				fileWriter.write("\n");
				fileWriter.write("#Project\n");		
				fileWriter.write(project.title+"\n");
				fileWriter.write(project.name+"\n");
				
				fileWriter.write(project.flagEmergency+"\n");
				fileWriter.write(project.flagPlay+"\n");
				fileWriter.write(project.flagArchive+"\n");
				fileWriter.write(project.flagReminder+"\n");
				
				fileWriter.write(project.dateStartVal.toString()+"\n");
				fileWriter.write(project.dateEndVal.toString()+"\n");
				fileWriter.write(project.dateReminderVal.toString()+"\n");
				
				fileWriter.write(project.startHour+"\n"); 
				fileWriter.write(project.endHour+"\n");
				fileWriter.write(project.reminderHour+"\n");	
				
				fileWriter.write(project.category+"\n");
			}
			for (Project project : Data.main.projectsList)
			{
				ObservableList <Node> boardsChildren = project.blackBoardReturn().dynamicBoardGetter().getChildren();
				
				for (Node item : boardsChildren)
				{
					if (item instanceof TextArea)
					{
						fileWriter.write("#TextArea Project: "+project.getText()+"\n");
						fileWriter.write(((TextArea)item).getText()+"\n");
						fileWriter.write("#End\n");
					}
					else if (item instanceof Label)
					{
						fileWriter.write("#Label Project: "+project.getText()+"\n");
						fileWriter.write(((Label)item).getText()+"\n");
						fileWriter.write("#End\n");
					}
				}
			}
			fileWriter.write("#Note\n");
			fileWriter.write(Data.timeAxis.notes.getText());
			fileWriter.write("\n#End");
			fileWriter.close();
		}
	}
	private void archiveProtector(Project project)
	{
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		
		if (dateNow.isAfter(project.dateStartVal) 
				&& project.flagPlay == false && project.flagArchive == false)
		{
			project.setOpacity(1);
			project.flagPlay = true;
		}
		if (dateNow.isAfter(project.dateEndVal) && !project.flagArchive 
				|| dateNow.isEqual(project.dateEndVal) && timeNow.isAfter(LocalTime.of(project.endHourInt, 0)) && !project.flagArchive)
		{
			project.flagPlay = false;
			project.flagArchive = true;
			project.flagReminder = false;
			project.setOpacity(0.2);
			project.setBackground(new Background(new BackgroundFill(project.color, new CornerRadii(20), new Insets(5))));
			
			Data.dynamicScreens.infoIcon.setVisible(true);
			Data.dynamicScreens.infoIcon.setDisable(false);
			
			Data.main.kolejkaAlertow.add(project.getText());	
		}
		else if (project.flagReminder && dateNow.isAfter(project.dateReminderVal))
		{
			project.setBackground(new Background(new BackgroundFill(Color.DARKRED, new CornerRadii(20), new Insets(5))));
			project.flagReminder = false;
		}
		if (project.flagArchive == true && project.getOpacity() != 0.2) project.setOpacity(0.2);
	}
	
}
