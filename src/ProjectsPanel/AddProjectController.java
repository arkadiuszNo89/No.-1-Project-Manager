package ProjectsPanel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javafx.util.Callback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.Data;


public class AddProjectController {

    @FXML
    private BorderPane root;
    
    @FXML
    private Button buttonAdd;

    @FXML
    private TextField TextTitle;

    @FXML
    private TextField TextName;

    @FXML
    private DatePicker DateStart;

    @FXML
    private DatePicker DateEnd;

    @FXML
    private ImageView IconEmergency;

    @FXML
    private ImageView IconClock;

    @FXML
    private DatePicker DateReminder;

    @FXML
    private Label LabelReminder;
    
    @FXML
    private Label labelHowLong;

    @FXML
    private ImageView IconPlay;
    
    @FXML
    private ImageView Cat1;

    @FXML
    private ImageView Cat2;

    @FXML
    private ImageView Cat3;

    @FXML
    private ImageView Cat4;

    @FXML
    private ImageView Cat5;

    @FXML
    private ImageView Cat6;
    
    @FXML
    private Label InfoLabel;
    
    @FXML
    private Spinner<String> spinnerEndHour;
    
    @FXML
    private Spinner<String> spinnerStartHour;

    @FXML
    private Spinner<String> spinnerReminderHour;
    

    public static AddProjectController tempAddProjectController;    
    private int category;
    private int projectAloudPointer;
    private int alertNumber;
    private int howLong; 
    private boolean firstClickTextField = false;   
    private Callback<DatePicker, DateCell> dayCellFactoryStart, dayCellFactoryReminder;
    private SpinnerValueFactory<String> valueFactory1, valueFactory2, valueFactory3; 
    private ImageView [] iconsTab;
    
    @FXML
    void initialize()
    {
    	iconsTab = new ImageView[6];
    	iconsTab[0] = Cat1;
    	iconsTab[1] = Cat2;
    	iconsTab[2] = Cat3;
    	iconsTab[3] = Cat4;
    	iconsTab[4] = Cat5;
    	iconsTab[5] = Cat6;
    	
    	datePickersInit();
    	textFieldsInit();		
    	primalState();
    	
    	tempAddProjectController = this;
    	root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    @FXML
    void ClickCancel(ActionEvent event) 
    {
    	Data.main.sceneSetter(Data.main.sceneProjectsScreenController);
    	primalState();
    	if (Data.main.flagaEditProject) Data.main.flagaEditProject = false;
    }

    @FXML
    void ClickClock(MouseEvent event) 
    {
    	checkReminders();
    }

    @FXML
    void ClickEmergency(MouseEvent event) 
    {
    	if (IconEmergency.getOpacity() != 1) IconEmergency.setOpacity(1);
    	else IconEmergency.setOpacity(0.3);
    }

    @FXML
    void ClickTextName(MouseEvent event) 
    {
    	TextName.setOpacity(1.0);
    	firstClickTextField = true; 
    }
    
//    KATEGORIE:
    
    @FXML
    void ClickCat1(MouseEvent event) 
    {
    	chooseCategory(1);
    }

    @FXML
    void ClickCat2(MouseEvent event) 
    {
    	chooseCategory(2);
    }

    @FXML
    void ClickCat3(MouseEvent event) 
    {
    	chooseCategory(3);
    }

    @FXML
    void ClickCat4(MouseEvent event) 
    {
    	chooseCategory(4);
    }

    @FXML
    void ClickCat5(MouseEvent event) 
    {
    	chooseCategory(5);
    }

    @FXML
    void ClickCat6(MouseEvent event) 
    {
    	chooseCategory(6);
    }
    
/*
* 		DODANIE PROJEKTU NA WARUNKACH
*/
        @FXML
        void ClickAdd(ActionEvent event) throws IOException 
        {
        	if (category == 0)	alerts(1);
        	else if (category > 0)	projectAloudPointer++;
        	
        	if (DateStart.getValue() == null) 
        		{
        			alerts(2);
        		}
        	else if (DateStart.getValue() != null)
    		{
    			projectAloudPointer++;
    			if (alertNumber == 2) restartAlerts();
    		}
        	if (DateEnd.getValue() == null) alerts(3);
        	else if (DateEnd.getValue() != null)
        		{
        			projectAloudPointer++;
        			if (alertNumber == 3) restartAlerts();
        		}
        	if (TextTitle.getLength() == 0)	alerts(4);
        	else if (TextTitle.getLength() > 0)	
        		{
        			projectAloudPointer++;
        			if (alertNumber == 4) restartAlerts();
        		}
        	if (checkIfTitleIsAvailable(TextTitle.getText())) 
        		{
	    			projectAloudPointer++;
	    			if (alertNumber == 5) restartAlerts();
        		}
        	else alerts(5);
        	if (DateStart.getValue().isEqual(DateEnd.getValue()) && 
        			Integer.parseInt(spinnerStartHour.getValue().substring(0,2)) > Integer.parseInt(spinnerEndHour.getValue().substring(0, 2))) alerts(6);
        	else
        	{
    			projectAloudPointer++;
    			if (alertNumber == 6) restartAlerts();
        	}
        	if (IconClock.getOpacity()==1)
        	{
	        	if (Integer.parseInt(spinnerStartHour.getValue().substring(0,2)) > Integer.parseInt(spinnerReminderHour.getValue().substring(0, 2))
	        			&&	DateStart.getValue().isEqual(DateReminder.getValue())
	        		|| Integer.parseInt(spinnerEndHour.getValue().substring(0,2)) < Integer.parseInt(spinnerReminderHour.getValue().substring(0, 2))
	        			&& DateEnd.getValue().isEqual(DateReminder.getValue())) 	alerts(7);  
	        	else
	        	{
	    			projectAloudPointer++;
	    			if (alertNumber == 7) restartAlerts();
	        	}
        	}
        	else 
        	{
    			projectAloudPointer++;
    			if (alertNumber == 7) restartAlerts();
        	}
        	
        	if (projectAloudPointer == 7)
        	{
//    	    	W przypadku, gdy usun¹³em tekst z name, a zostawiam je wolne
    	    	if (TextName.getText().length() == 0) TextName.setText(TextTitle.getText());
    	    	
    	    	Project newProj = new Project(TextTitle.getText()); 

//    	    	Nazwa (1):
    	    	newProj.theBigSetter_13Steps(TextName.getText());
//    	    	Flagi (2-5):
    	    	if (IconEmergency.getOpacity() == 1) newProj.theBigSetter_13Steps("true");
    	    	else newProj.theBigSetter_13Steps("false");
    	    	newProj.theBigSetter_13Steps("false");
    	    	newProj.theBigSetter_13Steps("false");
    	    	if (IconClock.getOpacity() == 1) newProj.theBigSetter_13Steps("true");
    	    	else newProj.theBigSetter_13Steps("false");
//    	    	Czas (6-11):
    	    	newProj.theBigSetter_13Steps(DateStart.getValue().toString());
    	    	newProj.theBigSetter_13Steps(DateEnd.getValue().toString());
    	    	newProj.theBigSetter_13Steps(DateReminder.getValue().toString());
    	    	newProj.theBigSetter_13Steps(spinnerStartHour.getValue());
    	    	newProj.theBigSetter_13Steps(spinnerEndHour.getValue());
    	    	newProj.theBigSetter_13Steps(spinnerReminderHour.getValue());
//    	    	Kategoria (12):
    	    	newProj.theBigSetter_13Steps(this.category+"");
//    	    	Inicjalizacja pozosta³ych = krok 13, ukryty
    	    	
    	    	if (Data.main.flagaEditProject)
    	    	{
    	    		newProj.blackBoardReturn().dynamicBoardGetter().getChildren()
    	    			.addAll(Data.main.actualProject.blackBoardReturn().dynamicBoardGetter().getChildren());
    	    		Data.main.removeProject(Data.main.actualProject);
    	    		Data.main.flagaEditProject = false;
    	    	}  	    	
    	    	
    	    	setOpacityByLevel(newProj, currentDateCheck());
    	    	if (newProj.getOpacity() == 1) newProj.flagPlay = true;
    	    	else if (newProj.getOpacity() == 0.2) newProj.flagArchive = true;
    	
//    	    	SPRAWDZENIE CZY NIE MA ZA WIELE PROJEKTOW, DODANIE DO TIMETABLE, A POZNIEJ INICJALIZACJA ISTNIENIA PROJEKTU
    	    	if (Data.main.year2020.input(newProj, false)) 
    	    		{
//    	    	    	DODANIE DO LISTY PROJEKTOW, DYNAMIC BUTTONS, LAYOUTOWEJ LISTY
    	    		Data.main.addToLists(newProj);    	    	    	
    	    	    	
//    	    	    	ZMIANA EKRANU NA EKRAN PROJEKTOW
    	    			Data.main.sceneSetter(Data.main.sceneProjectsScreenController);
    	    	    	Data.dynamicScreens.setDynamicButtonsScreen();
    	    	    	
//    	    	    	PRZYWROCENIE STANU ZERO
    	        		primalState();
    	        		restartAlerts();
    	    		}
    	    	else 
    	    		{
    	        		InfoLabel.setText("Przekroczono limit projektów dla wybranego dnia");
    	        		InfoLabel.setVisible(true);
    	    		}
        	}
        	else projectAloudPointer = 0;
        }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// Metody /////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void blindCategoriesImages()
    {
    	for (ImageView icon : iconsTab) icon.setOpacity(0.3);
    }
    public void chooseCategory (int iconNumber)
    {
    	blindCategoriesImages();
    	iconsTab[iconNumber-1].setOpacity(1);
    	category = iconNumber;
    	if (alertNumber == 1) restartAlerts();
    }
    
    private void primalState()
    {
    	InfoLabel.setVisible(false);
    	InfoLabel.setText("");
    	category = 0;
    	blindCategoriesImages();
    	projectAloudPointer = 0;
    	alertNumber = 0;
    	TextName.setText("");
    	TextName.setOpacity(0.3);
    	TextTitle.setText("");
    	
    	DateStart.setValue(LocalDate.now());
    	DateReminder.setValue(DateStart.getValue().plusDays(1));
    	DateEnd.setValue(DateStart.getValue().plusDays(1));
    	DateEnd.setDayCellFactory(dayCellFactoryStart);

    	howLong = 1;
    	labelHowLong.setText(howLong+" day");
    	
    	stateVis(false, DateReminder, LabelReminder, spinnerReminderHour);
    	IconClock.setOpacity(0.3);
    	valueFactory1.setValue("12.00");
    	valueFactory2.setValue("12.00");
    	valueFactory3.setValue("12.00");
    }
    
    private void alerts (int alertNumber)
    {
    	switch (alertNumber)
    	{
    	case 1:
    		InfoLabel.setText("Kategoria nie zosta³a wybrana");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 1;
    		break;
    	case 2:
    		InfoLabel.setText("Nie wybrano daty rozpoczêcia");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 2;
    		break;
    	case 3:
    		InfoLabel.setText("Nie wybrano daty zakoñczenia");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 3;
    		break;
    	case 4:
    		InfoLabel.setText("Projekt bez tytu³u");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 4;
    		break;
    	case 5:
    		InfoLabel.setText("Istnieje projekt o tej samej nazwie");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 5;
    		break;
    	case 6:
    		InfoLabel.setText("Niepoprawne godziny rozpoczêcia - zakoñczenia");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 6;
    		break;
    	case 7:
    		InfoLabel.setText("Niepoprawna godzina Remindera");
    		InfoLabel.setVisible(true);
    		this.alertNumber = 7;
    		break;
    	}
    }
    
    private void restartAlerts()
    {
		InfoLabel.setText("");
		InfoLabel.setVisible(false);
		alertNumber = 0;
    }
    
    /*
     * 		INICJALIZACJA PÓL TEKSTOWYCH TITLE I NAME
     */    
    @FXML
    void pressKeyTextName(KeyEvent event) {
        if(TextName.getText().length() == 0) 
        {
        	TextName.setOpacity(0.3);
        	firstClickTextField = false;
        }
        else if (TextName.getText().length() != 0 && TextName.getOpacity() != 1) 
    	{
    		TextName.setOpacity(1); 
    		firstClickTextField = true;
    	}
    }
    private void textFieldsInit()
    {
    	TextTitle.setPromptText("Title represents its Icon");
    	TextName.setPromptText("Name desribes the Project");
    	
//    	LISTENER DLA ZMIAN W POLU TITLE
    	TextTitle.textProperty().addListener(new ChangeListener<String>() 
    	{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
		        try 
		        {
		            if (newValue.length() > 16) TextTitle.setText(oldValue);
		            if (firstClickTextField == false) TextName.setText(observable.getValue());
		        }
		        catch (Exception e) 
		        {
		        	TextTitle.setText(oldValue);
		        }	
			}
    	});
//    	LISTENER DLA ZMIAN W POLU NAME
    	TextName.textProperty().addListener(new ChangeListener<String>() 
    	{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
		        try 
		        {
		            if(newValue.length() > 45) TextName.setText(oldValue);
		        } 
		        catch (Exception e) 
		        {
		        	TextName.setText(oldValue);
		        }	
			}
    	});
    }
    
    private void datePickersInit()
    {
/*
 *   	DATE PICKERS
 */  	
//    	BLOKUJE DNI, KTORE POPRZEDZAJA DATESTART
        dayCellFactoryStart = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) 
                    {
                        return new DateCell() 
                        {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) 
                            {
                                super.updateItem(item, empty);
                               
                                if (item.isBefore(DateStart.getValue())) 
                                { 
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    	}};
                    	
		dayCellFactoryReminder = new Callback<DatePicker, DateCell>() {
		    @Override
		    public DateCell call(final DatePicker datePicker) 
		    {
		        return new DateCell() 
		        {
		            @Override
		            public void updateItem(LocalDate item, boolean empty) 
		            {
		                super.updateItem(item, empty);

		                if (item.isAfter(DateEnd.getValue())) 
		                { 
		                        setDisable(true);
		                        setStyle("-fx-background-color: #ffc0cb;");
		                }
		                if (item.isBefore(DateStart.getValue())) 
		                { 
		                        setDisable(true);
		                        setStyle("-fx-background-color: #ffc0cb;");
		                }
		            }
		        };
		    }};
             

        DateStart.valueProperty().addListener((obsv, oldv, newv) -> 
        {
        	DateEnd.setDayCellFactory(dayCellFactoryStart);
        	DateReminder.setDayCellFactory(dayCellFactoryReminder);
        	if (DateEnd.getValue() != null)	
        	{
        		if (DateEnd.getValue().isBefore(newv))	DateEnd.setValue(newv.plusDays(1));
        		howLong = (int) ChronoUnit.DAYS.between(newv, DateEnd.getValue());
        		if (howLong == 1) labelHowLong.setText(howLong+" day");
        		if (howLong != 1) labelHowLong.setText(howLong+" days");
        		
        		if (currentDateCheck()==1)	IconPlay.setOpacity(1);
        		else IconPlay.setOpacity(0.3);    
        		
        		if (DateStart.getValue().isAfter(DateEnd.getValue())) DateEnd.setValue(DateStart.getValue().plusDays(1));
        	}
        	if (DateReminder.getValue() != null && DateStart.getValue().isAfter(DateReminder.getValue())) DateReminder.setValue(DateStart.getValue()); 
        	
        });
        DateEnd.valueProperty().addListener(
        		(obsv, oldv, newv) ->
        		{
        			DateReminder.setDayCellFactory(dayCellFactoryReminder);
        			if (DateReminder.getValue().isAfter(DateEnd.getValue())) DateReminder.setValue(DateEnd.getValue());
        			
            		howLong = (int) ChronoUnit.DAYS.between(DateStart.getValue(), newv);
            		if (howLong == 1) labelHowLong.setText(howLong+" day");
            		if (howLong != 1) labelHowLong.setText(howLong+" days");
            		
            		if (currentDateCheck()==1)		IconPlay.setOpacity(1);
            		else IconPlay.setOpacity(0.3);               		
        		});
        
/*
 *      SPINNERS
 */
        ObservableList <String> tempStringList = FXCollections.observableArrayList();
        for (int x = 0; x < 24; x++)	
        {
        	if (x<10) tempStringList.add("0"+x+".00");
        	else tempStringList.add(x+".00");
        }
        
        valueFactory1 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(tempStringList);
        valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(tempStringList);
        valueFactory3 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(tempStringList);
        
        spinnerEndHour.setValueFactory(valueFactory1);
        spinnerStartHour.setValueFactory(valueFactory2);
        spinnerReminderHour.setValueFactory(valueFactory3);
        
        spinnerStartHour.valueProperty().addListener(
        		(obsv, oldv, newv) -> 
        		{
            		if (currentDateCheck()==1)		IconPlay.setOpacity(1);
            		else IconPlay.setOpacity(0.3); 
            		
            		if (DateStart.getValue().isEqual(DateEnd.getValue()))
            		{
            			if (Integer.parseInt(spinnerStartHour.getValue().substring(0,2)) > Integer.parseInt(spinnerEndHour.getValue().substring(0, 2)))
            			{
            				valueFactory1.setValue(spinnerStartHour.getValue());
            			}
            		}
        		});
        spinnerEndHour.valueProperty().addListener(
        		(obsv, oldv, newv) -> 
        		{
            		if (currentDateCheck()==1)		IconPlay.setOpacity(1);
            		else IconPlay.setOpacity(0.3);   
            		
            		if (DateStart.getValue().isEqual(DateEnd.getValue()))
            		{
            			if (Integer.parseInt(spinnerStartHour.getValue().substring(0,2)) > Integer.parseInt(spinnerEndHour.getValue().substring(0, 2)))
            			{
            				valueFactory2.setValue(spinnerEndHour.getValue());
            			}
            		}
        		});
    }
    private void stateVis(boolean state, Control... objects)
    {
    	for (Control item : objects)
    	{
    		item.setDisable(!state);
    		if (state) item.setOpacity(1);
    		else item.setOpacity(0.3);
    	}
    }
    
    private int currentDateCheck ()
    {
    	LocalDate dateStart = DateStart.getValue();
    	LocalDate dateEnd = DateEnd.getValue();
    	
		LocalTime projectStartTime = LocalTime.of(Integer.parseInt(spinnerStartHour.getValue().substring(0, 2)), 0);
		LocalTime projectEndTime = LocalTime.of(Integer.parseInt(spinnerEndHour.getValue().substring(0, 2)), 0); 
    	
		LocalTime currentTime = LocalTime.now();
		LocalDate currentDate = LocalDate.now();
		
    	boolean flagaStart = false;
    	boolean flagaEnd = false;   	
    	
    	if ((currentDate.isAfter(dateStart))
    			||	(currentDate.isEqual(dateStart) && currentTime.isAfter(projectStartTime)))
    		flagaStart = true;
    	
    	if ((currentDate.isBefore(dateEnd))
    			||	(currentDate.isEqual(dateEnd) && currentTime.isBefore(projectEndTime)))
    		flagaEnd = true;  	
    	
    	if (flagaStart&&flagaEnd)	return 1;
    	else if (currentDate.isBefore(dateStart))	return 2;
    	else if (currentDate.isAfter(dateEnd))	return 3;
    	return 0;   	
    } 
    
    public int currentDateCheck (LocalDate dateStart, LocalDate dateEnd, String StartHour, String EndHour)
    {	
		LocalTime projectStartTime = LocalTime.of(Integer.parseInt(StartHour.substring(0, 2)), 0);
		LocalTime projectEndTime = LocalTime.of(Integer.parseInt(EndHour.substring(0, 2)), 0); 
    	
		LocalTime currentTime = LocalTime.now();
		LocalDate currentDate = LocalDate.now();
		
    	boolean flagaStart = false;
    	boolean flagaEnd = false;   	
    	
    	if ((currentDate.isAfter(dateStart))
    			||	(currentDate.isEqual(dateStart) && currentTime.isAfter(projectStartTime)))
    		flagaStart = true;
    	
    	if ((currentDate.isBefore(dateEnd))
    			||	(currentDate.isEqual(dateEnd) && currentTime.isBefore(projectEndTime)))
    		flagaEnd = true;  	
    	
    	if (flagaStart&&flagaEnd)	return 1;
    	else if (currentDate.isBefore(dateStart))	return 2;
    	else if (currentDate.isAfter(dateEnd))	return 3;
    	return 0;   	
    } 
    
    public void setOpacityByLevel(Project project, int level)
    {
    	switch(level)
    	{
    		case 1:
    			project.setOpacity(1);
    			break;
    		case 2:
    			project.setOpacity(0.7);
    			break;
    		case 3:
    			project.setOpacity(0.2);
    			break;
    	}
    }
    
//    SPRAWDZAM CZY TYLUL PROJEKTU SIE NIE POWTARZA
    private boolean checkIfTitleIsAvailable(String title)
    {   	 	
    	if (Data.main.flagaEditProject) return true;
    	for (Project item : Data.main.projectsList)
    	{
    		if (title.equals(item.getText())) return false;
    	}
    	return true;
    }
    
    private void checkReminders()
    {
    	if (IconClock.getOpacity() != 1)
    	{     	
        	stateVis(true, DateReminder, LabelReminder, spinnerReminderHour);
        	IconClock.setOpacity(1);
    	}
    	else
    	{      	
        	stateVis(false, DateReminder, LabelReminder, spinnerReminderHour);
        	IconClock.setOpacity(0.3);
    	}
    }
    
    public void setPanel()
    {
    	Project project = Data.main.actualProject;    	
    	TextTitle.setText(project.title);
    	TextName.setText(project.name);    	
    	DateStart.setValue(project.dateStartVal);
    	DateEnd.setValue(project.dateEndVal);
    	DateReminder.setValue(project.dateReminderVal);    	
    	if (project.flagReminder) checkReminders();
    	if (project.flagEmergency) IconEmergency.setOpacity(1);   	
    	spinnerStartHour.getValueFactory().setValue(project.startHour);
    	spinnerEndHour.getValueFactory().setValue(project.endHour);
    	spinnerReminderHour.getValueFactory().setValue(project.reminderHour);    	
    	chooseCategory(project.category);
    }
}




