package extras;

import java.util.Comparator;
import ProjectsPanel.Project;


public class SortByNames implements Comparator<Project>
{

	@Override
	public int compare(Project o1, Project o2) 
	{	
		if (!o1.flagArchive && !o2.flagArchive)
		{
			if (o1.toString() == o2.toString()) return 0;			
			return o1.toString().compareTo(o2.toString());
		}
		else if (o1.flagArchive && o2.flagArchive)
		{
			if (o1.toString() == o2.toString()) return 0;			
			return o1.toString().compareTo(o2.toString());
		}
		else if (!o1.flagArchive && o2.flagArchive) return -1;
		else if (o1.flagArchive && !o2.flagArchive) return 1;

		return 0;
	}
}
