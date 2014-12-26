package sicassem;

import java.util.*;


public class ObjectProg{
//Fields
private HeaderRecord head;
private EndRecord end;
private ArrayList<TextRecord> text;

//constructors
public ObjectProg()
{
	head=new HeaderRecord();
	end=new EndRecord();
	text=new ArrayList<TextRecord>();

}

public ObjectProg(HeaderRecord hr)
{
	head=hr;
	end=new EndRecord();
	text=new ArrayList<TextRecord>();
}

public ObjectProg(HeaderRecord hr,EndRecord er)
{
	head=hr;
	end=er;
	text=new ArrayList<TextRecord>();
}

public ObjectProg(HeaderRecord hr,EndRecord er,ArrayList<TextRecord> tr)
{
	head=hr;
	end=er;
	text=tr;
}


//Getter and setter methods
public HeaderRecord getHeader()
{	return head;}
public ArrayList<TextRecord>	getTextRecords()
{	return text;}
public EndRecord getEnd()
{	return end;}


public void setHead(HeaderRecord hr)
{	head=hr;}
public void setEnd(EndRecord er)
{	end=er;}
public void addTextRecord(TextRecord tr)
{	text.add(tr);}
public void addTextList(ArrayList<TextRecord> tlist)
{	text.addAll(tlist);}


//Method to convert to string
public String toString()
{
	StringBuilder obj=new StringBuilder();
	obj.append(head);
	obj.append("\n");
	for(TextRecord tr:text)
	{
		obj.append(tr);	obj.append("\n");
	}
	obj.append("\n");
	obj.append(end);
	return obj.toString();
}



}
