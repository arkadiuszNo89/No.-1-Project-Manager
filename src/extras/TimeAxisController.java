package extras;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import ProjectsPanel.BlackBoard;
import ProjectsPanel.Project;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Data;
import main.Main;


public class TimeAxisController {

    @FXML
    public BorderPane timeTablePane;
    
    @FXML
    private BorderPane blackBoardViewer;
    
    @FXML
    private Rectangle rectangle1;

    @FXML
    private Rectangle rectangle2;

    @FXML
    private Rectangle rectangle3;

    @FXML
    private Rectangle rectangle4;

    @FXML
    private Rectangle rectangle5;

    @FXML
    private Rectangle rectangle6;

    @FXML
    private Rectangle rectangle7;

    @FXML
    private Label weekNr;

    @FXML
    private Label weekNrMinus;

    @FXML
    private Label weekNrPlus;

    @FXML
    private Label dateLab1;

    @FXML
    private Label dateLab2;

    @FXML
    private Label dateLab3;

    @FXML
    private Label dateLab4;

    @FXML
    private Label dateLab5;

    @FXML
    private Label dateLab6;

    @FXML
    private Label dateLab7;

    @FXML
    private Label dateLabPrev1;

    @FXML
    private Label dateLabPrev2;

    @FXML
    private Label dateLabPrev3;

    @FXML
    private Label dateLabPrev4;

    @FXML
    private Label dateLabPrev5;

    @FXML
    private Label dateLabPrev6;

    @FXML
    private Label dateLabPrev7;

    @FXML
    private Label dateLabNext1;

    @FXML
    private Label dateLabNext2;

    @FXML
    private Label dateLabNext3;

    @FXML
    private Label dateLabNext5;

    @FXML
    private Label dateLabNext4;

    @FXML
    private Label dateLabNext6;

    @FXML
    private Label dateLabNext7;

    @FXML
    private Label qtyNext1;

    @FXML
    private Label qtyNext2;

    @FXML
    private Label qtyNext3;

    @FXML
    private Label qtyNext4;

    @FXML
    private Label qtyNext5;

    @FXML
    private Label qtyNext6;

    @FXML
    private Label qtyNext7;

    @FXML
    private Label qty1;

    @FXML
    private Label qty2;

    @FXML
    private Label qty3;

    @FXML
    private Label qty4;

    @FXML
    private Label qty5;

    @FXML
    private Label qty6;

    @FXML
    private Label qty7;

    @FXML
    private Label qtyPrev1;

    @FXML
    private Label qtyPrev2;

    @FXML
    private Label qtyPrev3;

    @FXML
    private Label qtyPrev4;

    @FXML
    private Label qtyPrev5;

    @FXML
    private Label qtyPrev6;

    @FXML
    private Label qtyPrev7;

    @FXML
    public ListView<String> listLayout;

    @FXML
    private Button buttonGoMain;

    @FXML
    private Button buttonSetProject;
    
    @FXML
    private Button buttonOpenWindow;

    @FXML
    public Spinner<String> spinnerWeekNr;

    @FXML
    public TextArea notes;
    
//  VARS
    private SpinnerValueFactory<String> valueFactory;
    public static TimeAxisController timeAxis;
    public static Main main;
    public Label actualLabel;
    public boolean calendarEntry;
    public int currentWeekNumber;    
    private ArrayList<Label> qtyLabelsList;
    private ArrayList<Label> qtyLabelsListPrev;
    private ArrayList<Label> qtyLabelsListNext;
    private ArrayList<Label> dateLabelsList;
    private ArrayList<Label> dateLabelsListPrev;
    private ArrayList<Label> dateLabelsListNext;  
    private ArrayList<Rectangle> rectangleList;
    
    @FXML
    void initialize()
    {
    	initLabels();
    	initSpinner();
    	initRectangles();
    	TimeAxisController.timeAxis = this;
    	blackBoardViewer.setVisible(false);
    	calendarEntry = false;
    	actualLabel = dateLab5;
    	
    	timeTablePane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    @FXML
    void clickMain(ActionEvent event) 
    {
    	main.sceneSetter(main.sceneMainScreen);
    	listLayout.getItems().clear();
    }
    @FXML
    void clickGoLogs(ActionEvent event) 
    {
    	Data.main.sceneSetter(main.sceneLogs);
    }

    @FXML
    void clickGoProjectScreen(ActionEvent event) 
    {
    	Data.main.sceneSetter(main.sceneProjectsScreenController);
    }
    
    @FXML
    void clickGoProject(ActionEvent event) 
    {
    	if (!listLayout.getItems().isEmpty() && !blackBoardViewer.isVisible() && !listLayout.getSelectionModel().isEmpty())
    	{
        	for (Project item : main.projectsList)
        	{		
        		if (listLayout.getSelectionModel().getSelectedItem().toString() == item.toString()) 
        		{        			
        			if (BlackBoard.myTextAreaMarked != null)
        			{
           				BlackBoard.myTextAreaMarked.setEffect(null);
           				BlackBoard.myTextAreaMarked = null;
        			}
	        			blackBoardViewer.setCenter(item.blackBoardGetter());
	        			onOffBlackBoardViewer(true);
	        			Data.main.actualProject = item;
       			}	
        	}
    	}
    	else if (blackBoardViewer.isVisible())	
    		{
    			onOffBlackBoardViewer(false);
    			Data.timeAxis.labelsSet(Data.timeAxis.spinnerWeekNr.getValue());
    		}
    }
    
    @FXML
    void clickOpenWindow(ActionEvent event) 
    {
    	BorderPane root = new BorderPane();
    	Stage window = new Stage();
    	TextArea notesWindow = new TextArea();
    	
    	root.setPrefSize(500, 300);
    	window.setResizable(false);
    	
    	notesWindow.setWrapText(true);
    	notesWindow.setText(notes.getText());
    	notes.setEditable(false);
    	buttonOpenWindow.setDisable(true);
    	
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) 
			{
				notes.setText(notesWindow.getText());
				notes.setEditable(true);
				buttonOpenWindow.setDisable(false);
			}       	
		});
    	
    	root.setCenter(notesWindow);    	
    	Scene scene = new Scene(root);
    	window.setScene(scene);
    	window.show();
    }
    
    private void initSpinner()
    {
        ObservableList <String> tempStringList = FXCollections.observableArrayList();
        for (int x = 1; x <= 53; x++)	
        {
        	if (x<10) tempStringList.add("0"+x);
        	else tempStringList.add(x+"");
        }
        
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(tempStringList);
        
        LocalDate date = LocalDate.now();
        TemporalField WeekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();  
        currentWeekNumber = date.get(WeekOfYear); 
        
        if (currentWeekNumber < 10)	valueFactory.setValue("0"+currentWeekNumber); 
        else 					valueFactory.setValue(""+currentWeekNumber);
            
        spinnerWeekNr.setValueFactory(valueFactory); 
        
        spinnerWeekNr.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) // roznica pomiedzy ????
			{				
				labelsSet(arg2);
			}
		});
    }
    
    private void initLabels()
    {
    	qtyLabelsList = new ArrayList<Label>();
    	qtyLabelsListPrev = new ArrayList<Label>();
        qtyLabelsListNext = new ArrayList<Label>();
    	dateLabelsList = new ArrayList<Label>();
    	dateLabelsListPrev = new ArrayList<Label>();
    	dateLabelsListNext = new ArrayList<Label>();
    	
    	qtyLabelsList.addAll(Arrays.asList(qty1,qty2,qty3,qty4,qty5,qty6,qty7));
    	qtyLabelsListPrev.addAll(Arrays.asList(qtyPrev1,qtyPrev2,qtyPrev3,qtyPrev4,qtyPrev5,qtyPrev6,qtyPrev7));
    	qtyLabelsListNext.addAll(Arrays.asList(qtyNext1,qtyNext2,qtyNext3,qtyNext4,qtyNext5,qtyNext6,qtyNext7));
    	dateLabelsList.addAll(Arrays.asList(dateLab1,dateLab2,dateLab3,dateLab4,dateLab5,dateLab6,dateLab7));
    	dateLabelsListPrev.addAll(Arrays.asList(dateLabPrev1,dateLabPrev2,dateLabPrev3,dateLabPrev4,dateLabPrev5,dateLabPrev6,dateLabPrev7));
    	dateLabelsListNext.addAll(Arrays.asList(dateLabNext1,dateLabNext2,dateLabNext3,dateLabNext4,dateLabNext5,dateLabNext6,dateLabNext7)); 
    	
    	ArrayList <ArrayList<Label>> totalTemp = new ArrayList <ArrayList<Label>> ();
    	totalTemp.addAll(Arrays.asList(dateLabelsListPrev, dateLabelsList, dateLabelsListNext));
    	
    	for (ArrayList <Label> lista : totalTemp)
    	{
    		for (Label item : lista)
    		{
        		item.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

        			@Override
        			public void handle(MouseEvent arg0) 
        			{
        				actualLabel = item;
        				handle_clickDateLabel (item);      				
        			}
        		});
    		}
    	}
    }
    
    private void initRectangles()
    {
    	rectangleList = new ArrayList<Rectangle>();
    	rectangleList.addAll(Arrays.asList(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, rectangle7));
    	
    	for (Rectangle item : rectangleList)
    	{
    		item.setFill(javafx.scene.paint.Color.AZURE);
    	}
    }
    
    public void labelsSet (String arg2)
    {
//    	NUMERY TYGODNIA
		weekNr.setText(arg2);
		int x = Integer.parseInt(arg2);
		
		if (x > 10) weekNrMinus.setText(""+(x-1));
		else if (x > 1) weekNrMinus.setText("0"+(x-1));
		else weekNrMinus.setText(" ");		

		if (x < 9) weekNrPlus.setText("0"+(x+1));
		else if (x < 53) weekNrPlus.setText(""+(x+1));
		else weekNrPlus.setText(" ");		

//		WSTAWIANIE DNI MIESIACA I LICZBY PROJEKTOW
		for (int y = 0; y < 7; y++) 
			{
				dateLabelsList.get(y).setText(main.year2020.daysOfYear[Integer.parseInt(arg2)-1][y].getDayOfMonth()+"");
				qtyLabelsList.get(y).setText(main.year2020.daysProjectsCounter[Integer.parseInt(arg2)-1][y]+"");
			}
		if (Integer.parseInt(arg2) > 1) 
		{
			for(int y = 0; y < 7; y++) 
				{
					dateLabelsListPrev.get(y).setText(main.year2020.daysOfYear[Integer.parseInt(arg2)-2][y].getDayOfMonth()+"");
					qtyLabelsListPrev.get(y).setText(main.year2020.daysProjectsCounter[Integer.parseInt(arg2)-2][y]+"");
				}
		}
		else 
			{
				for(int y = 0; y < 7; y++)
					{
						dateLabelsListPrev.get(y).setText(" ");
						qtyLabelsListPrev.get(y).setText(" ");
					}
			}
		if (Integer.parseInt(arg2) < 53) 
		{
			for(int y = 0; y < 7; y++) 
				{
					dateLabelsListNext.get(y).setText(main.year2020.daysOfYear[Integer.parseInt(arg2)][y].getDayOfMonth()+"");
					qtyLabelsListNext.get(y).setText(main.year2020.daysProjectsCounter[Integer.parseInt(arg2)][y]+"");
				}
		}
		else 
		{
			for(int y = 0; y < 7; y++)
				{
					dateLabelsListNext.get(y).setText(" ");
					qtyLabelsListNext.get(y).setText(" ");
				}
		}
    }
    
    public void onOffBlackBoardViewer(boolean mode)
    {
		blackBoardViewer.setVisible(mode); 
		listLayout.setVisible(!mode);
		notes.setVisible(!mode);
		buttonOpenWindow.setVisible(!mode);
		spinnerWeekNr.setVisible(!mode);
		
		if (mode)
		{
			buttonSetProject.setText("OFF");
		}
		else
		{
			buttonSetProject.setText("ON");
			blackBoardViewer.getChildren().clear();
		}
    }
    
    public void handle_clickDateLabel (Label item)
    {
		listLayout.getItems().clear();
		
		if (item.getId().contains("Prev"))
		{
			int xFOR = Integer.parseInt(spinnerWeekNr.getValue())-2;
			int yFOR = Integer.parseInt(String.valueOf(item.getId().charAt(11)))-1;
			
			if (xFOR >= 0)
			{
				for (String name : main.year2020.oneYearData[xFOR][yFOR])
				{
					if (name == null) break;
					listLayout.getItems().add(name);
				}
			}
		}
		else if (item.getId().contains("Next"))
		{
			int xFOR = Integer.parseInt(spinnerWeekNr.getValue());
			int yFOR = Integer.parseInt(String.valueOf(item.getId().charAt(11)))-1;
			
			if (xFOR < 53)
			{
				for (String name : main.year2020.oneYearData[xFOR][yFOR])
				{
					if (name == null) break;
					listLayout.getItems().add(name);
				}
			}
		}
		else
		{
			int xFOR = Integer.parseInt(spinnerWeekNr.getValue())-1;
			int yFOR = Integer.parseInt(String.valueOf(item.getId().charAt(7)))-1;
			
			for (String name : main.year2020.oneYearData[xFOR][yFOR])
			{
				if (name == null) break;
				listLayout.getItems().add(name);
			}			
		}   
    }
}




