package sicassem;

import java.util.*;


public class EndRecord{
//Fields
private int start;
//Constructors
public EndRecord()
{
}

public EndRecord(int strt)
{
start=strt;
}

//Getter and setter methods
public int getStart()
{	return start;}
public void setStart(int strt)
{	strt=start;}

//Method to convert to string
public String toString()
{
	StringBuilder end=new StringBuilder(9);
	end.append("E^");
	end.append(String.format("%06x",start));
	return end.toString();
}
}
