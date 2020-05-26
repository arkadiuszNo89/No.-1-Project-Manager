package extras;

import javafx.scene.control.Alert;

public class Dialog 
{	
	public static void showDialogAlert(String name)
	{
		Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
		informationAlert.setTitle("Info Alert");
		informationAlert.setHeaderText("Project is over");
		informationAlert.setContentText(name);
		
		informationAlert.show();
	}
}
