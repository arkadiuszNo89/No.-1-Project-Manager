package ProjectsPanel;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;


public class Icons extends BorderPane
{
    private BorderPane rootDynamicButtons;
    private ScrollPane scroll;
    private FlowPane dynamicButtonsPane;
    
    public static FlowPane dynamicButtonsPaneTemp;
    
    public Icons()
    {
    	rootDynamicButtons = this;
        scroll = new ScrollPane();
        dynamicButtonsPane = new FlowPane();       
        this.setCenter(scroll);
        scroll.setContent(dynamicButtonsPane);
        dynamicButtonsPaneTemp = dynamicButtonsPane;
        dynamicButtonsPane.prefWidthProperty().bind(scroll.widthProperty());
        scroll.prefWidthProperty().bind(rootDynamicButtons.widthProperty());     
        
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
