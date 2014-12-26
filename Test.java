import sicassem.*;
import java.util.*;
public class Test
{


static public void main(String args[])
{
  String str;
  StringBuilder prog=new StringBuilder();
  Scanner s=new Scanner(System.in);
  System.out.println("Enter the program:");
  do
  {
    str=s.nextLine();
    prog.append(str);
    prog.append("\n");
  }
  while(!str.toUpperCase().contains("END"));
  System.out.println(prog.toString());
  try
  {
    Assembler ass=new Assembler(prog.toString());
    ass.assemble();
    ExecEngine myEng=new ExecEngine(ass.getObject());
    myEng.exec();
    myEng.debug();
  }
  catch(Exception e)
  {
    System.out.println(e);
  }
 }

}
