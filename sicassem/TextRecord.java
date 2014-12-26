package sicassem;

import java.util.*;


public class TextRecord{
//Fields
private final int MAXLEN=30;
private int start,bytes,ctr;
private ArrayList<String> rec;

//Constructors
public TextRecord()
{
	rec=new ArrayList<String>();
	ctr=0;
	bytes=0;
}

public TextRecord(int strt)
{	start=strt;
	ctr=0;
	bytes=0;
	rec=new ArrayList<String>();	}

public TextRecord(int strt,ArrayList<String> list)
{
rec=new ArrayList<String>();
ctr=0;
bytes=0;
for(String a:list)
{
	rec.add(a);
	bytes+=a.length()/2;
	if(bytes>MAXLEN)
	{
		rec.remove(rec.lastIndexOf(a));
		bytes-=a.length()/2;
	}
}
}

//Getter and setter methods
public int getStart()
{	return start;}
public int getBytes()
{	return bytes;}
public ArrayList<String> getRecord()
{	return rec;}
public boolean isFull()
{	return bytes>=MAXLEN;}

public void setStart(int strt)
{	start=strt;}

public boolean add(String s)
{
if(bytes+s.length()/2<=MAXLEN)
{
rec.add(s);
bytes+=s.length()/2;
return true;
}
else	return false;
}

//Method for getting a string
public String toString()
{
	StringBuilder text=new StringBuilder();
	text.append("T^");
	text.append(String.format("%06x",start));	text.append("^");
	text.append(String.format("%02x",bytes));
	for(String a:rec)
	{
		text.append("^");
		text.append(a);
	}
	return text.toString();
}

}
