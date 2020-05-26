package extras;

import ProjectsPanel.BlackBoard;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;


public class myTextArea extends TextArea
{
	public myTextArea()
	{
		this.setEditable(false);
		
		this.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				if (!BlackBoard.editMode)
				{
					doSomeLight();
				}
			}
		});
		this.setWrapText(true);
		 
	}
//	W TEJ METODZIE MARKUJE ZAZNACZONY ELEMENT DO ZMIENNEJ STATIC I WYKORZYSTUJE TO DO MANIPULACJI EFEKTAMI LUB USUWANIA
	public void doSomeLight()
	{
		if (BlackBoard.markedObject != null)
		{
			if (BlackBoard.markedObject instanceof Label)
			{
				Label temp = (Label) BlackBoard.markedObject;
				temp.setBackground(Background.EMPTY);
				BlackBoard.markedObject = null;
			}
			if (BlackBoard.markedObject instanceof ImageView)
			{
				ImageView temp = (ImageView) BlackBoard.markedObject;
				temp.setEffect(null);
				BlackBoard.markedObject = null;
			}
		}		
		if (BlackBoard.myTextAreaMarked == null)
		{
			this.setEffect(new Lighting());
			BlackBoard.myTextAreaMarked = this;
		}
		else if (BlackBoard.myTextAreaMarked == this)
		{
			this.setEffect(null);
			BlackBoard.myTextAreaMarked = null;
		}
		else
		{
			BlackBoard.myTextAreaMarked.setEffect(null);
			BlackBoard.myTextAreaMarked = this;
			this.setEffect(new Lighting());
		}
	}
}