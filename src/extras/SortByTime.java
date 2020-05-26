package extras;

import java.util.Comparator;
import ProjectsPanel.Project;

public class SortByTime implements Comparator<Project>
{

	@Override
	public int compare(Project o1, Project o2) 
	{	
//		if (o1.dateEndVal.getDayOfYear() < o2.dateEndVal.getDayOfYear()) return -1;
//		else if (o1.dateEndVal.getDayOfYear() > o2.dateEndVal.getDayOfYear()) return 1;
//		else if (o1.dateEndVal.getDayOfYear() == o2.dateEndVal.getDayOfYear())
//		{
//			if (o1.endHourInt < o2.endHourInt) return -1;
//			else if (o1.endHourInt > o2.endHourInt) return 1;
//			else if (o1.endHourInt == o2.endHourInt) return 0;
//		}
//		return 0;
		

		
		if (!o1.flagArchive && !o2.flagArchive)
		{
			if (o1.dateEndVal.getDayOfYear() < o2.dateEndVal.getDayOfYear()) return -1;
			else if (o1.dateEndVal.getDayOfYear() > o2.dateEndVal.getDayOfYear()) return 1;
			else if (o1.dateEndVal.getDayOfYear() == o2.dateEndVal.getDayOfYear())
			{
				if (o1.endHourInt < o2.endHourInt) return -1;
				else if (o1.endHourInt > o2.endHourInt) return 1;
				else if (o1.endHourInt == o2.endHourInt) return 0;
			}
		}
		if (o1.flagArchive && o2.flagArchive)
		{
			if (o1.dateEndVal.getDayOfYear() < o2.dateEndVal.getDayOfYear()) return 1;
			else if (o1.dateEndVal.getDayOfYear() > o2.dateEndVal.getDayOfYear()) return -1;
			else if (o1.dateEndVal.getDayOfYear() == o2.dateEndVal.getDayOfYear())
			{
				if (o1.endHourInt < o2.endHourInt) return 1;
				else if (o1.endHourInt > o2.endHourInt) return -1;
				else if (o1.endHourInt == o2.endHourInt) return 0;
			}
		}
		
		else if (!o1.flagArchive && o2.flagArchive) return -1; 
		else if (o1.flagArchive && !o2.flagArchive) return 1;

		return 0;
	}

}
