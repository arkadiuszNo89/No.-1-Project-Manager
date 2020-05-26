package extras;

import ProjectsPanel.BlackBoard;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class EditTextPane extends BorderPane
{
	public EditTextPane(BlackBoard blackBoard, Stage stage)
	{
		TextArea area = new TextArea();
		Button buttonSave = new Button("Save");
		Button buttonCancel = new Button("Cancel");
		HBox hBox = new HBox();
		
		this.setCenter(area);
		this.setBottom(hBox);
		hBox.getChildren().addAll(buttonCancel, buttonSave);
		hBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setMargin(buttonSave, new Insets(5, 10, 5, 5));
		HBox.setMargin(buttonCancel, new Insets(5, 5, 5, 5));
		
		this.setPrefSize(400, 400);
		area.setWrapText(true);
		
		if (BlackBoard.myTextAreaMarked != null) area.setText(BlackBoard.myTextAreaMarked.getText());
		else area.setText("¯adne pole tekstowe nie jest zaznaczone");
		
		
		buttonSave.setOnAction((ActionEvent event) ->
		{
			if (BlackBoard.myTextAreaMarked != null) 
			{
				BlackBoard.myTextAreaMarked.setText(area.getText());
				BlackBoard.editMode = false;
				stage.close();
			}
		});
		
		buttonCancel.setOnAction((ActionEvent event) ->
		{
			BlackBoard.editMode = false;
			stage.close();
		});
		
		stage.setOnCloseRequest((WindowEvent event) ->
		{
			BlackBoard.editMode = false;
			System.out.println(BlackBoard.editMode);
		});	
	}
}
