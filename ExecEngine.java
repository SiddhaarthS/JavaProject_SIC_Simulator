import sicassem.*;
import java.util.*;

public class ExecEngine{
//Execution engine that will have values for memory and registers
//Register fields
private int reg_A,reg_L,reg_PC,reg_X,strt,end;
private Hashtable<Integer,Integer> memory; //Memory hash map for easy access
private ObjectProg myObj;
//Constructor
public ExecEngine()
{
	memory=new Hashtable<Integer,Integer>();
}

public ExecEngine(ObjectProg ob)
{
	memory=new Hashtable<Integer,Integer>();
	myObj=ob;
}

//Helper method to initialise the memory
private void init()
{
	strt=myObj.getHeader().getStartAddr();
	end=strt+myObj.getHeader().getLength();
	for(int i=strt;i<=end;i++)
		memory.put(i,0xFF);
	reg_PC=strt;
}

//Getter and setter functions for memory
public void setMem(int addr,int val)
{
	memory.put(addr,val);
}

public int getMem(int addr)
{
	int val;
	val=memory.get(addr);
	return val;	
}

//Gettert methods for registers. Setting of register valuesis not allowed
int getA()
{	return reg_A;}

int getX()
{	return reg_X;}

int getL()
{	return reg_L;}

int getPC()
{	return reg_PC;}

//Setting the object program
void setProg(ObjectProg p)
{	myObj=p;	}


//Method to begin the execution of the engine
//To be called after the object program is passed and begins the execution
//Version 1.0 - works only for load and store and basic arithmetic operations with direct addressing only
public void exec()
{
if(myObj==null)
{
	System.out.println("No  object program to execute !");
	return;
}
init();
ArrayList<TextRecord> txtList=myObj.getTextRecords();
for(TextRecord txt:txtList)
{
	int strt=txt.getStart();
	reg_PC=strt;
	ArrayList<String> recs=txt.getRecord();
	for(String s:recs)
	{
		reg_PC+=s.length()/2;
		int val=Integer.parseInt(s,16);
		//System.out.println("Executing instruction : "+val);
		int mask=0x00FF;
		int i=1;
		while(i<=s.length()/2)
		{
			memory.put(reg_PC-i,(val&mask)>>(8*(i-1)));
			mask<<=8;
			i++;
		}
		if(s.length()==6)
		{
			int opc=(val&0xFF0000);
			opc=opc>>16;
			int addr=val&0x00FFFF;
			perform(opc,addr);
		}		
	}
}
}

//Helper function to perform operation directed by the opcode
private void perform(int opc,int addr)
{
//System.out.println("Received in perform : opc:"+opc+"addr:"+addr); 
if(opc==0x00){ //LDA instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x0C){//STA instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		putMem(addr,reg_A);
	//else
		//System.out.println("Invalid address!");
}
else if(opc==0x04){//LDX instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_X=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x10){//STX instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		putMem(addr,reg_X);
	//else
		//System.out.println("Invalid address!");
}
else if(opc==0x08){//LDL instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_L=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x14){//STL instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		putMem(addr,reg_L);
	//else
		//System.out.println("Invalid address!");
}
else if(opc==0x18){//ADD instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A+=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x1C){//SUB instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A-=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x20){//MUL instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A*=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x24){//DIV instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A/=getVal(addr);
	//else
		//System.out.println("Invalid address !");
}
if(opc==0x50){ //LDCH instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		reg_A=memory.get(addr);
	//else
		//System.out.println("Invalid address !");
}
else if(opc==0x54){//STCH instruction code
	Integer value=memory.get(addr);
	if(value!=null)
		memory.put(addr,reg_A);
	//else
		//System.out.prio1    ntln("Invalid address!");
}
}

//Helper function for easy memory access
private void putMem(int addr,int val)
{
	memory.put(addr,(val&0xFF0000)>>16);
	memory.put(addr+1,(val&0x00FF00)>>8);
	memory.put(addr+2,(val&0x0000FF));
}

private int getVal(int addr)
{
int value=memory.get(addr);
value=value<<8;
value+=memory.get(addr+1);
value=value<<8;
value+=memory.get(addr+2);
//System.out.println("returning value for "+addr+"= "+value);
return value;
}

//Debugging method to print all data
public String debug()
{	
	StringBuilder string=new StringBuilder();
	/*System.out.println("Register values : ");
	System.out.println("A: "+reg_A);
	System.out.println("L: "+reg_L);
	System.out.println("X: "+reg_X);
	System.out.println("PC: "+reg_PC);
	System.out.println("Memory trace :");*/

	for(int i=strt;i<=end;i++)
		string.append(i+"  "+String.format("%02x",memory.get(i)).toUpperCase()+"\n");
	return string.toString();
}

}//End of ExecEngine class
