package extras;

import ProjectsPanel.BlackBoard;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;

public class MarkAndEdit<T extends Label, TextArea, ImageView> 
{

	public T object;
	
	
	public MarkAndEdit(T object)
	{
		this.object = object;
	}
	
	
	public void doSomeChanges()
	{
		object.setEffect(new Lighting());
		
		if (BlackBoard.markedObject == null)
		{
			object.setEffect(new Lighting());
			BlackBoard.markedObject = this;
		}
		else if (BlackBoard.markedObject == object)
		{
			object.setEffect(null);
			BlackBoard.markedObject = null;
		}
		else
		{
//			BlackBoard.markedObject.setEffect(null);
			
			T temp = (T)BlackBoard.markedObject;
			temp.setEffect(null);
			
			BlackBoard.markedObject = object;
			object.setEffect(new Lighting());
		}
	}
}
