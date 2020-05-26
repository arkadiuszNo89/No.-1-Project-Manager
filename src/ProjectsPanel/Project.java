package ProjectsPanel;

import java.time.LocalDate;
import java.util.ArrayList;
import extras.myTextArea;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.Data;


public class Project extends Button {
	
	private BlackBoard blackBoard;
	
	public static int qty = 1;
	public int id;
	public String title;
	public String name;
	
	public boolean flagEmergency;
	public boolean flagReminder;
	public boolean flagPlay;
	public boolean flagArchive;
	
	public LocalDate dateStartVal;
	public LocalDate dateEndVal;
	public LocalDate dateReminderVal;
	
	public String startHour;
	public String endHour;
	public String reminderHour;
	public int startHourInt;
	public int endHourInt;
	public int reminderHourInt;	
	public int category;
	public Color color; 
	
	private final Project thisProject;
	
	public ArrayList <myTextArea> proj_textAreaList;
	public ArrayList <Label> proj_labelList; 
	public ArrayList <String> proj_pctsList; 
	Color [] colorOfButtonArray;
	
	private int theBigSetterCounter;

	
			  ////////////////////////////////////
			 ///    INICJALIZACJA PROJEKTU	  ///
			////////////////////////////////////

	public Project(String buttonText)
	{
		super(buttonText);
		this.title = buttonText;
		thisProject = this;
		id = qty;
		qty++;
		theBigSetterCounter = 0;
		colorOfButtonArray = new Color [] {Color.ORANGE, Color.LIGHTSKYBLUE, Color.CORAL, 
										Color.YELLOW, Color.LIGHTGREY, Color.LIGHTGREEN};
		
		proj_textAreaList = new ArrayList <myTextArea> ();
		proj_labelList = new ArrayList <Label> (); 
		proj_pctsList = new ArrayList <String> (); 
	}
			  ////////////////////////////////////
			 ///                              ///
			////////////////////////////////////
	
	public BorderPane blackBoardGetter()
	{
		return blackBoard;
	}
	public BlackBoard blackBoardReturn()
	{
		return this.blackBoard; 
	}
	
	@Override
	public String toString()
	{
		return this.getText();
	}
	
	public void loadInit()
	{
		blackBoard = new BlackBoard(this);
		blackBoard.initialize();
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) 
			{
				Data.main.actualProject = thisProject;
				
//				PO WYBRANIU PROJEKTU ROBIE DODATKOWO ZMIANE NA LISCIE, ZEBY SIE POKRYWALY
				Data.listController.ProjectsListView.setCenter(thisProject.blackBoardGetter()); 
				Data.listController.projectsListLayout.getSelectionModel().select(thisProject.getText());
				
//				PRZEJSCIE DO EKRANU PROJEKTU , CZY BLACKBOARDU
				Data.dynamicScreens.rootPane.setCenter(thisProject.blackBoardGetter());
			}
		});
	}
	public boolean theBigSetter_13Steps(String string)
	{	
		theBigSetterCounter++;
		switch(theBigSetterCounter)
		{
			case 1: this.name = string; break;
			case 2:	this.flagEmergency = Boolean.valueOf(string); break;
			case 3:	this.flagPlay = Boolean.valueOf(string); break;
			case 4: this.flagArchive = Boolean.valueOf(string); break;
			case 5: this.flagReminder = Boolean.valueOf(string); break;
			case 6: this.dateStartVal = LocalDate.parse(string); break;
			case 7: this.dateEndVal = LocalDate.parse(string); break;
			case 8: this.dateReminderVal = LocalDate.parse(string); break;
			case 9: this.startHour = string; break;
			case 10: this.endHour = string; break;
			case 11: this.reminderHour = string; break;
			case 12: 
				{
					this.category = Integer.parseInt(string.substring(0,1));
					theBigSetterCounter++;
				}
			case 13:
			{
				theBigSetterCounter=0;
				this.startHourInt = Integer.parseInt(this.startHour.substring(0, 2));
				this.endHourInt = Integer.parseInt(this.endHour.substring(0, 2));
				this.reminderHourInt = Integer.parseInt(this.reminderHour.substring(0, 2));
				this.color = colorOfButtonArray[this.category-1]; 
				if (this.flagEmergency) this.setStyle("-fx-font-weight: bold;");
				this.setPrefSize(150, 150);
				this.setBackground(new Background(new BackgroundFill(this.color, new CornerRadii(20), new Insets(5))));
				loadInit();
				Data.textLogs.appendText("Dodano projekt: "+this.title+"\n");
				
				return false;
			}	
		}
		return true;
	}
}
