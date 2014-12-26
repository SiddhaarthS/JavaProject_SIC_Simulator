package sicassem;

import java.util.*;

public class SymTab{
//The hash table
private Hashtable<String,Integer> addr;
//private Hashtable<String,ArrayList<Integer>> fwdRefList;

//Constructor
public SymTab()
{
	addr=new Hashtable<String,Integer>();
}

//Interfacing methods
public void putVal(String name,int address)
{
	addr.put(name,address);
}

public int getAddr(String name)
{
	Integer ad=addr.get(name);
	if(ad==null)	return -1;
	else return ad;
}


//Method to print the values in the table
public String toString()
{
	StringBuilder hashTab=new StringBuilder();
	Enumeration keys=addr.keys();
	while(keys.hasMoreElements())
	{
		String k=(String)keys.nextElement();
		hashTab.append(k+"\t"+addr.get(k)+"\n");
	}
	return hashTab.toString();
}
}
