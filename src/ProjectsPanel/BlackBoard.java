package ProjectsPanel;

import extras.EditTextPane;
import extras.myTextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Data;


public class BlackBoard extends BorderPane
{

	private FlowPane board;
	private HBox bottomBar;
	private ScrollPane scroll;
	private BorderPane thisBoarderPane;
	private BlackBoard thisBlackBoard;
	private Project projectInside;
	
	private VBox line1;
	private HBox line2;
	
	private Label labelName;
	public TextField textField; 
	private Button buttonAdd;
	private Button buttonEdit;
	private Button buttonDelete;
	
	private Label labelDateStart;
	private Label labelDateEnd;
	private Label labelHourStart; 
	private Label labelHourEnd;
	private Label labelRemindDate; 
	private Label labelRemindHour;
	
	private Region region1;
	private Region region2;
	
	private EventHandler <ActionEvent> mainAction1;
	private EventHandler <ActionEvent> mainAction2;
	private EventHandler <ActionEvent> mainAction3;
	public EventHandler <ActionEvent> addText;
//	private EventHandler <ActionEvent> addPct;
	public EventHandler <ActionEvent> addLabel;
	private EventHandler <ActionEvent> editTextArea;
	private EventHandler <ActionEvent> editProject;
	private EventHandler <ActionEvent> deleteMarked;
	private EventHandler <ActionEvent> deleteProject;
	private EventHandler <ActionEvent> cancel;
	
	public static myTextArea myTextAreaMarked;
	public static boolean editMode = false;
	public static Object markedObject; 
	public static Object freshNode; 
	
	public BlackBoard (Project project)
	{
		thisBoarderPane = this;
		thisBlackBoard = this;
		this.projectInside = project;
		
//		TOP
		region1 = new Region();
		region2 = new Region();
		
		line1 = new VBox();
		line2 = new HBox();
		line2.prefWidthProperty().bind(this.widthProperty());
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region2, Priority.ALWAYS);
		
		labelName = new Label(project.name);
		labelName.setAlignment(Pos.CENTER);
		labelName.setFont(new Font("SimSun", 22));
		labelName.setStyle("-fx-font-weight: bold;");
		labelName.prefWidthProperty().bind(this.widthProperty());
		
		labelDateStart = new Label (project.dateStartVal.toString());	
		labelDateStart.setFont(new Font("SimSun", 14));
		labelDateEnd = new Label (project.dateEndVal.toString());
		labelDateEnd.setFont(new Font("SimSun", 14));
		labelHourStart = new Label (project.startHour);
		labelHourStart.setFont(new Font("SimSun", 14));
		labelHourEnd = new Label (project.endHour);
		labelHourEnd.setFont(new Font("SimSun", 14));
		labelRemindDate = new Label (project.dateReminderVal.toString());
		labelRemindDate.setFont(new Font("SimSun", 14));
		labelRemindHour = new Label (project.reminderHour); 
		labelRemindHour.setFont(new Font("SimSun", 14));
		
		labelDateStart.setPadding(new Insets(0,0,0,10));
		labelHourStart.setPadding(new Insets(0,0,0,10));
		labelDateEnd.setPadding(new Insets(0,10,0,0));
		labelHourEnd.setPadding(new Insets(0,10,0,0));
		labelRemindDate.setPadding(new Insets(0,10,0,0));
		
		if (!project.flagReminder) 
		{
			labelRemindDate.setVisible(false);
			labelRemindHour.setVisible(false);
		}
		
//		CENTER
		scroll = new ScrollPane();
		board = new FlowPane();
		
//		BOTTOM
		buttonAdd = new Button("Add");
		buttonEdit = new Button("Edit");
		buttonDelete = new Button("Delete");
		buttonAdd.setPrefWidth(57);
		buttonEdit.setPrefWidth(57);
		buttonDelete.setPrefWidth(57);
		textField = new TextField();
		
		bottomBar = new HBox(textField, buttonDelete, buttonEdit, buttonAdd);
		bottomBar.setSpacing(10);
		bottomBar.setAlignment(Pos.CENTER_RIGHT);
		HBox.setMargin(buttonAdd, new Insets(5,10,5,0));
		HBox.setMargin(buttonEdit, new Insets(5,0,5,0));
		HBox.setMargin(buttonDelete, new Insets(5,0,5,0));
		
	}
	
	public void initialize()
	{
		thisBoarderPane.setTop(line1);
		line1.getChildren().add(labelName);
		line1.getChildren().add(line2);		
		line2.getChildren().addAll(labelDateStart, labelHourStart, region1, labelRemindDate, labelRemindHour, region2, labelDateEnd, labelHourEnd);
		
		thisBoarderPane.setCenter(scroll);
		scroll.setContent(board);
		thisBoarderPane.setBottom(bottomBar);
		
		board.prefWidthProperty().bind(thisBoarderPane.widthProperty());
		scroll.prefWidthProperty().bind(thisBoarderPane.widthProperty());
		
		textField.setPrefWidth(200);
    	textField.textProperty().addListener(new ChangeListener<String>() 
    	{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
		        try 
		        {
		            if (newValue.length() > 50) textField.setText(oldValue);
		        }
		        catch (Exception e) 
		        {
		        	textField.setText(oldValue);
		        }	
			}
    	});
    	textField.setVisible(false);
    	
    	scroll.setOnMouseClicked((Event arg0) 
    			-> {
	    				if (myTextAreaMarked != null)
	    				{
	    					myTextAreaMarked.setEffect(null);
	    					myTextAreaMarked = null;
	    				}	
    			   });
		addActionsToButtons();
	} 

		  ////////////////////////////////////////////////////////////
		 /// ZAZNACZANIE I EDYCJA TYTU£ÓW LUB ZDJÊÆ NA BLACKBOARD ///
		////////////////////////////////////////////////////////////
	
	
	public static void markAndEdit(Object object)
	{	
		if (myTextAreaMarked != null)
		{
			myTextAreaMarked.setEffect(null);
			myTextAreaMarked = null;
		}		
		if (object instanceof Label) 
		{
			Label temp = (Label) object;
			
			if (markedObject == null)
			{
				temp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
				markedObject = temp;
			}
			else if (markedObject == temp)
			{
				temp.setBackground(Background.EMPTY);
				markedObject = null;
			}
			else
			{
				if (markedObject instanceof Label)
				{
					Label temp2 = (Label) markedObject;
					temp2.setBackground(Background.EMPTY);
				}
				else if (markedObject instanceof ImageView)
				{
					ImageView temp2 = (ImageView) markedObject;
					temp2.setEffect(null);
				}
				markedObject = temp;
				temp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
		else if (object instanceof ImageView) 
		{
			ImageView temp = (ImageView) object;
			
			if (markedObject == null)
			{
				temp.setEffect(new Glow(1));
				markedObject = temp;
			}
			else if (markedObject == temp)
			{
				temp.setEffect(null);
				markedObject = null;
			}
			else
			{
				if (markedObject instanceof Label)
				{
					Label temp2 = (Label) markedObject;
					temp2.setBackground(Background.EMPTY);
				}
				else if (markedObject instanceof ImageView)
				{
					ImageView temp2 = (ImageView) markedObject;
					temp2.setEffect(null);
				}
				markedObject = temp;
				temp.setEffect(new Glow(1));
			}
		}	
	}
	
				  ////////////////////////////////////
				 /// DODAWANIE AKCJI DO BUTTONOW  ///
				////////////////////////////////////
	private void addActionsToButtons()
	{
		mainAction1 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				txtAmbientMode();
			}
		};
		
		mainAction2 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{		
				txtEdittMode();
			}
		};
		
		mainAction3 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				txtDeletetMode();
			}
		};
		
		addText = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{	
				myTextArea textArea = new myTextArea();
				textArea.setPrefSize(150,150);
				board.getChildren().add(textArea);	
				freshNode = textArea;
				txtNormalMode();
			}
		};
		
/*
 * 		Mo¿liwoœæ dodania zdjêæ jako trzeci obiekt dla BlackBoard
 */
		
//		addPct = new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//
//				try 
//				{
//					FileChooser fileChooser = new FileChooser();
//					fileChooser.getExtensionFilters().add(
//						     new FileChooser.ExtensionFilter("*.jpg", "*.jpeg", "*.JPG", "*.PNG"));
//					File file = fileChooser.showOpenDialog(null);	
//					if (file != null)
//					{
//						FileInputStream fileStream = new FileInputStream(file);
//						Image image = new Image(fileStream);
//						ImageView imageView = new ImageView(image);
//						file = null;
//						imageView.setFitWidth(205);
//						imageView.setPreserveRatio(true);
//						imageView.setOnMouseClicked(new EventHandler<Event>() {
//
//							@Override
//							public void handle(Event arg0) {
//								markAndEdit(imageView);
//							}
//						});
//						board.getChildren().add(imageView);
//					}
//				} 
//				catch (FileNotFoundException e) 
//				{
//					System.out.println("nie ma");
//				}
//				txtNormalMode();
//			}
//		};
		
		addLabel = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if (textField.getText().length() > 0)
				{
					Label blackBoardLabel = new Label(textField.getText());
					blackBoardLabel.prefWidthProperty().bind(thisBoarderPane.widthProperty());
					blackBoardLabel.setAlignment(Pos.CENTER);
					blackBoardLabel.setFont(new Font("SimSun", 18));
					projectInside.proj_labelList.add(blackBoardLabel);
					freshNode = blackBoardLabel;
					
					blackBoardLabel.setOnMouseClicked(new EventHandler<Event>() {

						@Override
						public void handle(Event arg0) {
							markAndEdit(blackBoardLabel);
						}
					});
					board.getChildren().add(blackBoardLabel);
				}
				txtNormalMode();
			}
		};
		
		editTextArea = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{		
				if (myTextAreaMarked != null && BlackBoard.myTextAreaMarked instanceof myTextArea)
				{
					editMode = true;
					Stage tempStage = new Stage();
					tempStage.setScene(new Scene(new EditTextPane(thisBlackBoard, tempStage)));
					tempStage.show();
					tempStage.setResizable(false);				
				}
			}
		};
		
		editProject = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{
				Data.dynamicScreens.AddProjectButton.fire();
				Data.main.flagaEditProject = true;				
				Data.addProjectController.setPanel();
			}
		};
		
		cancel = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{
				txtNormalMode();
			}
		};
		
		deleteMarked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) 
			{
				board.getChildren().remove(myTextAreaMarked);
				board.getChildren().remove(markedObject);
				
				projectInside.proj_textAreaList.remove(myTextAreaMarked);
				projectInside.proj_labelList.remove(markedObject);
				projectInside.proj_pctsList.remove(markedObject);
				
				myTextAreaMarked = null;
				markedObject = null;
				txtNormalMode();
			}
		};
		
		deleteProject = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) 
			{
				Data.main.removeProject(Data.main.actualProject);	
				txtNormalMode();
			}
		};	
		txtNormalMode();
	}
	
	private void setButtonTxts(String add, String edit, String delete)
	{
		buttonAdd.setText(add);
		buttonEdit.setText(edit);
		buttonDelete.setText(delete);
	}
	private void txtNormalMode()
	{
		setButtonTxts("Add", "Edit", "Delete");
		buttonAdd.setOnAction(mainAction1);
		buttonEdit.setOnAction(mainAction2);
		buttonDelete.setOnAction(mainAction3);
		
		textField.setVisible(false);
		textField.setText("");
		buttonDelete.setVisible(true);
	}
	private void txtAmbientMode()
	{
		setButtonTxts("Text", "Title", "Cancel");
		buttonAdd.setOnAction(addText);
		buttonEdit.setOnAction(addLabel);
		buttonDelete.setOnAction(cancel);
		textField.setVisible(true);
	}
	private void txtEdittMode()
	{
		setButtonTxts("Text", "Project", "Cancel");
		buttonAdd.setOnAction(editTextArea);
		buttonEdit.setOnAction(editProject);
		buttonDelete.setOnAction(cancel);
	}
	private void txtDeletetMode()
	{
		setButtonTxts("Project", "Marked", "Cancel");
		buttonAdd.setOnAction(deleteProject);
		buttonEdit.setOnAction(deleteMarked);
		buttonDelete.setOnAction(cancel);
	}
	
				  ////////////////////////////////////
				 ///            KONIEC            ///
				////////////////////////////////////
	
	public FlowPane dynamicBoardGetter()
	{
		return this.board;
	}
}
