package extras;


import java.time.LocalDate;
import java.time.temporal.IsoFields;

import ProjectsPanel.Project;

public class TimeTable {

	String [][][] oneYearData;
	int [][] daysProjectsCounter;
	LocalDate [][] daysOfYear;
	boolean arraysFall = false;		
	private final String [] daysOfWeek;
	String year;
	
	public TimeTable(String year)
	{
		daysOfWeek = new String [] {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
		oneYearData = new String [53][7][20];
		daysProjectsCounter = new int [53][7]; 
		for (int x = 0; x < 53; x++)
		{
			for (int y = 0; y < 7; y++) daysProjectsCounter[x][y] = 0; 
		}
		this.year = year;
		
		daysOfYearInit();
	}
	
	
	private void daysOfYearInit()
	{
		daysOfYear = new LocalDate [53][7];		
		int dayOfWeekNumStart = whichDayOfWeek(LocalDate.parse(year+"-01-01"));		
		LocalDate theDate = LocalDate.parse(year+"-01-01");
		
		for (int x = 0; x < 53; x++)
		{
			if (x == 0)
			{
				for (int y = 0; y <7; y++)
				{
					if (y == 0) theDate = theDate.minusDays(dayOfWeekNumStart);
					daysOfYear[x][y] = theDate;
					theDate = theDate.plusDays(1); 
				}
			}
			else if (x > 0)
			{
				for (int y = 0; y < 7; y++)
					{
						daysOfYear[x][y] = theDate;
						theDate = theDate.plusDays(1);
					}
			}
		}
	}
	
	public boolean input(Project project, boolean reverse)
	{	
		int startWeek = project.dateStartVal.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - 1;
		int endWeek = project.dateEndVal.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - 1;
		
		boolean flaga = false;
		int dayOfWeekNumStart = whichDayOfWeek(project.dateStartVal);
		int dayOfWeekNumEnd = whichDayOfWeek(project.dateEndVal);		
		
//		PRZYPISYWANIE
		
		for(int x = startWeek; x <= endWeek; x++)
		{			
			if (!flaga && startWeek != endWeek)
			{
				for (int y = dayOfWeekNumStart; y <7; y++)
				{					
					if (assigning(startWeek,endWeek, flaga, dayOfWeekNumStart, dayOfWeekNumEnd, reverse, project, x, y)) continue;
					else return false;
				}
			} 
			else if (!flaga && startWeek == endWeek)
			{
				for (int y = dayOfWeekNumStart; y <= dayOfWeekNumEnd; y++)
				{
					if (assigning(startWeek,endWeek, flaga, dayOfWeekNumStart, dayOfWeekNumEnd, reverse, project, x, y)) continue;
					else return false;
				}
			}
			if (flaga && x!=endWeek)
			{
				for (int y = 0; y <7; y++)
				{
					if (assigning(startWeek,endWeek, flaga, dayOfWeekNumStart, dayOfWeekNumEnd, reverse, project, x, y)) continue;
					else return false;
				}
			}
			else if (flaga && x==endWeek)
			{
				for (int y = 0; y <= dayOfWeekNumEnd; y++)
				{
					if (assigning(startWeek,endWeek, flaga, dayOfWeekNumStart, dayOfWeekNumEnd, reverse, project, x, y)) continue;
					else return false;
				} 
			}
			flaga = true;
		}
		return true; 
	}
	
	public int whichDayOfWeek (LocalDate date)
	{
		int counter = 0;
		
		for (String item : this.daysOfWeek)
		{
			if (date.getDayOfWeek().toString() == item) return counter;
			counter++;
		}
		return -1;
	}
	
	private boolean assigning(int startWeek, int endWeek, boolean flaga, int dayOfWeekNumStart, int dayOfWeekNumEnd, boolean reverse, Project project, int x, int y)
	{		
		if (daysProjectsCounter[x][y] == 20 && !reverse) return false;
		if (!reverse)	daysProjectsCounter[x][y]++;
		else if (reverse)	daysProjectsCounter[x][y]--;
		
		for (int z = 0; z < oneYearData[0][0].length; z++)
		{
			if (oneYearData[x][y][z] == null && !reverse)
			{
				oneYearData[x][y][z] = project.getText();
				break;
			}
			else if (oneYearData[x][y][z] == project.getText() && reverse)
			{
				oneYearData[x][y][z] = null;
				arraysFall = true;
			}
			if (arraysFall && z < oneYearData[0][0].length - 1)
			{
				if (oneYearData[x][y][z+1] != null) 
				{
					oneYearData[x][y][z] = oneYearData[x][y][z+1];
					oneYearData[x][y][z+1] = null;
				}
				else
				{
					arraysFall = false;
					break;
				}
			}
		}
		return true;
	}
}
