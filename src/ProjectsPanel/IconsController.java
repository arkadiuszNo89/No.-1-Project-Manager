package ProjectsPanel;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class IconsController {

//    @FXML
//    private FlowPane dynamicButtonAnchor;
    
    @FXML
    private BorderPane rootDynamicButtons;
    
    private ScrollPane scroll;
    private FlowPane dynamicButtonAnchor2;
    
    public static FlowPane dynamicButtonAnchorTemp;		// nowa sztuczka, teraz mogê WSZYSTKO

    @FXML
    void initialize()
    {
    	scroll = new ScrollPane();
    	dynamicButtonAnchor2 = new FlowPane();
    	
    	rootDynamicButtons.setCenter(scroll);
    	scroll.setContent(dynamicButtonAnchor2);
    	
    	IconsController.dynamicButtonAnchorTemp = this.dynamicButtonAnchor2;
    } 
}
