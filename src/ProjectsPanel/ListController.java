package ProjectsPanel;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.Data;


public class ListController {

    @FXML
    public ListView<String> projectsListLayout;
    
    @FXML
    public BorderPane ProjectsListView;
    
    public static ListView<String> projectsListLayoutTemp;
    public static BorderPane projectsListViewTemp;
    public static ListController listControllerTemp;

    @FXML
    void initialize()
    {
    	ListController.projectsListLayoutTemp = projectsListLayout;
    	ListController.projectsListViewTemp = ProjectsListView;
    	listControllerTemp = this;
    	
    	ProjectsListView.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    @FXML
    void clickList(MouseEvent event) 
    {	
    			Project project = Data.main.mapaNazw.get(projectsListLayout.getSelectionModel().getSelectedItem().toString());
    	
    			ProjectsListView.setCenter(project.blackBoardGetter());		
    			Data.main.actualProject = project;
    			
    			if (BlackBoard.myTextAreaMarked != null)
    			{
       				BlackBoard.myTextAreaMarked.setEffect(null);
       				BlackBoard.myTextAreaMarked = null;
    			}
    }
}
