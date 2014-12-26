package sicassem;

import java.util.*;

public class Optable{
static private Hashtable<String,Integer> opTab;
static void populate()
{
	opTab=new Hashtable<String,Integer>();
	opTab.put("LDA",0x00);
	opTab.put("STA",0x0C);
	opTab.put("ADD",0x18);
	opTab.put("AND",0x58);
	opTab.put("COMP",0x28);
	opTab.put("DIV",0x24);
	opTab.put("J",0x3C);
	opTab.put("JEQ",0x30);
	opTab.put("JGT",0x34);
	opTab.put("JLT",0x38);
	opTab.put("JSUB",0x48);
	opTab.put("LDCH",0x50);
	opTab.put("LDL",0x08);
	opTab.put("LDX",0x04);
	opTab.put("MUL",0x20);
	opTab.put("OR",0x44);
	opTab.put("RD",0xD8);
	opTab.put("STCH",0x54);
	opTab.put("STL",0x14);
	opTab.put("STSW",0xE8);
	opTab.put("STX",0x10);
	opTab.put("SUB",0x1C);
	opTab.put("TD",0xE0);
	opTab.put("TIX",0x2C);
	opTab.put("WD",0xDC);
}

static public int getOpCode(String key)
{
	Integer code=opTab.get(key.toUpperCase());
	if(code==null)	return -1;
	else return code;
}

}
