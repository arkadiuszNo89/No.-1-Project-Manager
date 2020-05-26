package ProjectsPanel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BlackBoardController {

    @FXML
    private Label LabelNameInsideBlackBoard;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonAdd;

    @FXML
    private VBox board;

    public void changeName(String txt)
    {
    	LabelNameInsideBlackBoard.setText(txt);
    }
}