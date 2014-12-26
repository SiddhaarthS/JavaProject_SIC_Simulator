package sicassem;

import java.util.*;


public class Assembler{

private SymTab stab;
private String program,name;
private String[] lines;
private String[] words;
private int addrs[],last_addr;
private ObjectProg myObj;

//Constructors
public Assembler()
{
	stab=new SymTab();
	Optable.populate();
}
public Assembler(String p)
{
	stab=new SymTab();
	program=p;
	lines=program.split("\n");
	addrs=new int[lines.length];
	Optable.populate();
}

//Helper methods
private boolean pass1()
{
int op,cur_addr;
words=lines[0].split("\\s+");
if(words[0].toUpperCase().equals("START"))
{
	addrs[1]=Integer.parseInt(words[1],16);
	name=" ";
}
else if(words[1].toUpperCase().equals("START"))
{
	addrs[1]=Integer.parseInt(words[2],16);
	name=words[0];
}
cur_addr=addrs[1];
addrs[0]=-1;
for(int i=1;i<lines.length;i++)
{
words=lines[i].split("\\s+");
/*
Testing code
for(int k=0;k<words.length;k++)
	System.out.println(words[k]);
*/
addrs[i]=cur_addr;
if(words.length==3)
{
stab.putVal(words[0],addrs[i]);
//System.out.println("Adding to table : "+words[0]+" and addrs: "+addrs[i]);
op=1;
}
else if(words.length==2)
{
op=0;
}
else if(words.length==1&&words[0].equals("END"))
{
	last_addr=cur_addr;
	break;
}
else return false;
if(Optable.getOpCode(words[op])!=-1){
	cur_addr=addrs[i]+3;
}
else{
if(words[op].toUpperCase().equals("END")){
	last_addr=cur_addr;
	break;
}
else if(words[op].toUpperCase().equals("RESW"))
{
	stab.putVal(words[op-1],cur_addr);
	cur_addr=addrs[i]+Integer.parseInt(words[op+1])*3;
}
else if(words[op].toUpperCase().equals("RESB"))
{
	stab.putVal(words[op-1],cur_addr);
	cur_addr=addrs[i]+Integer.parseInt(words[op+1]);
}
else if(words[op].toUpperCase().equals("WORD"))
{
	stab.putVal(words[op-1],cur_addr);
	cur_addr=addrs[i]+3;
}
else if(words[op].toUpperCase().equals("BYTE"))
{
	stab.putVal(words[op-1],cur_addr);
	if(words[op].toUpperCase().contains("C\'"))
	{
		String[] txt=words[op+1].split("\'");
		cur_addr+=txt[1].length();
	}
	else if(words[op].toUpperCase().contains("F\'"))
	{
		String[] txt=words[op+1].split("\'");
		cur_addr+=txt[1].length()/2;
	}
	else{
		cur_addr+=1;
	}
}
//Any other assembler directives to be added
//If the directive does not require address then set its addrs value to -1 
}
}
//System.out.println("Returning true!");
return true;
}//End of pass1 

private boolean pass2()
{
int op,addr;
words=lines[0].split("\\s+");
HeaderRecord hr=new HeaderRecord();
//Creating header record ////////////////////////////////
hr.setName(name);
hr.setStartAddr(addrs[1]);
hr.setLength(last_addr-hr.getStartAddr());
myObj.setHead(hr);
//Creating and adding text records ///////////////////////
TextRecord tr;
tr=new TextRecord(addrs[1]);
for(int i=1;i<lines.length;i++)
{
	words=lines[i].split("\\s+");
	//System.out.println(lines[i]);
	if(words.length==3)	op=1;
	else if(words.length==2)	op=0;
	else if(words.length==1&&words[0].equals("END"))
	{	if(tr.getBytes()>0)
			myObj.addTextRecord(tr);
		break;
	}
	else return false;
	StringBuilder opc=new StringBuilder();
	int opcode=Optable.getOpCode(words[op]);
	if(opcode!=-1)
	{
		opc.append(String.format("%02x",opcode));
		if(words[op+1].toUpperCase().contains(",X"))//Indexed addressing mode
		{
		String symbol=words[op+1].split(",")[0];
		addr=stab.getAddr(symbol);
		if(addr==-1)	return false;
		addr+=0x8000;
		opc.append(String.format("%04x",addr));
		}
		else{ //Non indexed mode
		addr=stab.getAddr(words[op+1]);
		if(addr==-1)	return false;//Symbol not found
		opc.append(String.format("%04x",addr));
		}
	}
	else if(words[op].equals("RESW"))
	{
		if(tr.getBytes()>0)
			myObj.addTextRecord(tr);
		tr=new TextRecord(addrs[i]+(Integer.parseInt(words[op+1])*3));
		continue;
	}
	else if(words[op].equals("RESB"))
	{
		if(tr.getBytes()>0)
			myObj.addTextRecord(tr);
		tr=new TextRecord(addrs[i]+(Integer.parseInt(words[op+1])));
		continue;
	}
	else if(words[op].equals("WORD"))
	{
		opc.append(String.format("%06x",Integer.parseInt(words[op+1])));
	}
	else if(words[op].equals("BYTE"))
	{
		if(words[op+1].contains("F\'"))
			opc.append(words[op+1].split("\'")[1]);
		else if(words[op+1].contains("C\'"))
		{
			for(int j=2;j<words[op+1].length()-1;j++)
				opc.append(String.format("%02x",(int)words[op+1].charAt(j)));
		}
		else opc.append(String.format("%02x",Integer.parseInt(words[op+1])));
	}
	//Any more assembler directives to be added come here
	else
	{
	return false;
	}
	if(!tr.add(opc.toString()))
	{
	myObj.addTextRecord(tr);
	tr=new TextRecord(addrs[i]);
	}
}
return true;
}//End of pass2

//Getter and setter methods
public void setProgram(String p)
{
	program=p;
	lines=program.split("\n");
}

public ObjectProg getObject()
{	return myObj;}

public SymTab getSymTab()
{	return stab;}
 
//Externally invoked method that can be accessed from outside
public void assemble() throws Exception
{
	myObj=new ObjectProg();
	if(pass1()){
		//System.out.println("Pass 1 success!\n Symbol table :\n");
		//System.out.println(stab.toString());
		if(pass2())
		{
			//System.out.println(myObj.toString());
			return;
		}
	}
	//System.out.println("Assembly errors !");
	throw new Exception("Assembly Error !");
}




}
