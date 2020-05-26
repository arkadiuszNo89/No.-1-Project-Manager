package extras;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimerTask;
import ProjectsPanel.Project;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.Data;


public class TimerAct extends TimerTask
{	
	@Override
	public void run() {
		
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		
		if (!Data.main.projectsList.isEmpty())
		{
			check:
			for (Project project : Data.main.projectsList)
			{					
//				PROJEKTY JESZCZE SIE NIE ZACZELY
				if (dateNow.isBefore(project.dateStartVal))	continue check;
				if (dateNow.isEqual(project.dateStartVal) && timeNow.isBefore(LocalTime.of(project.startHourInt, 0))) continue check;
				
//				PROJEKT STARTUJE
				if (dateNow.isEqual(project.dateStartVal) && timeNow.isAfter(LocalTime.of(project.startHourInt, 0)) 
						&& project.flagPlay == false && project.flagArchive == false)
				{
					project.setOpacity(1);
					project.flagPlay = true;
					continue check;
				} 
				
//				REMINDER CHECK
				if (project.flagReminder)
				{
					if (dateNow.isEqual(project.dateReminderVal) && timeNow.isAfter(LocalTime.of(project.reminderHourInt, 0)))
					{
						project.setBackground(new Background(new BackgroundFill(Color.DARKRED, new CornerRadii(20), new Insets(5))));
						project.flagReminder = false;
						System.out.println("reminder color on"); 
					}
				}			
//				PRZEKROCZONY ZOSTAJE CZAS PROJEKTU:
				if (dateNow.isEqual(project.dateEndVal))
				{
					LocalTime timeProject = LocalTime.of(project.endHourInt, 0);
					if (timeNow.isAfter(timeProject) && project.flagPlay == true)
					{
						project.flagPlay = false;
						project.flagArchive = true;
						project.flagReminder = false; 
						project.setOpacity(0.2);
						project.setBackground(new Background(new BackgroundFill(project.color, new CornerRadii(20), new Insets(5))));
						
						Data.dynamicScreens.infoIcon.setVisible(true);
						Data.dynamicScreens.infoIcon.setDisable(false);
						
						Data.main.kolejkaAlertow.add(project.getText());	
					}
				}
			}
		}
	}
}
