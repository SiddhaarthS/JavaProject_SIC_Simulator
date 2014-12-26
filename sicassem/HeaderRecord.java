package sicassem;

import java.util.*;

public class HeaderRecord{
private int startAddr,length;
private String progname;
//Constructors
public HeaderRecord(){
}

public HeaderRecord(int sAd,int len,String name)
{
	startAddr=sAd;
	length=len;
	progname=name;
}

//Getter and setter methods
public int getStartAddr()
{	return startAddr;}
public int getLength()
{	return length;}
public String getName()
{	return progname;}

public void setStartAddr(int sa)
{	startAddr=sa;}
public void setLength(int len)
{	length=len;}
public void setName(String name)
{	progname=name;}


//Method to get a string from it 
public String toString()
{
	StringBuilder head=new StringBuilder(23);
	head.append("H^");
	head.append(String.format("%-6s",progname)+"^");
	head.append(String.format("%06x",startAddr));	head.append("^");
	head.append(String.format("%06x",length));
	return head.toString();
}
}

