import sicassem.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

class panel 
{
JFrame frame = new JFrame();
Container cont=frame.getContentPane();		//returns the content pane layer so that you can add an abject to it
JTextField a = new JTextField(3);
JTextField x = new JTextField(3);
JTextField l = new JTextField(3);
JTextField pc = new JTextField(5);
JTextField sw = new JTextField(3);
JTextArea srcProgram =new JTextArea(12,20);
JTextArea objProgram =new JTextArea(12,20);
JTextArea symTable =new JTextArea(8,12);
JTextArea memDump =new JTextArea(5,20);

public panel()
{

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SIC Simulator");
		frame.pack();
		frame.setResizable(false);
		
		cont.setLayout(new BorderLayout());
//Creating the icon		
		ImageIcon imgIcon = new ImageIcon(UI.class.getResource("/logo.jpg"));
		frame.setIconImage(imgIcon.getImage());
//Creating the menu bar	
		MyMenuBar menuBar = new MyMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);	
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);	
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem run = new JMenuItem("Run");
		runMenu.add(run);
		JMenuItem compile = new JMenuItem("Compile");
		runMenu.add(compile);
		
		run.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
				try{
				Assembler ass=new Assembler(srcProgram.getText());
				ass.assemble();
				objProgram.setText(ass.getObject().toString());
				ExecEngine myEng=new ExecEngine(ass.getObject());
				myEng.exec();
				a.setText(" "+myEng.getA()+" ");
				x.setText(" "+myEng.getX()+" ");
				pc.setText(" "+myEng.getPC()+" ");
				l.setText(" 0 ");
				sw.setText(" 0 ");
				symTable.setText(ass.getSymTab().toString());
				memDump.setText(myEng.debug());
				}
				catch(Exception e)
				{
				objProgram.setText("ERROR!! "+e);
				}
		}
		});
		
		compile.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
				try{
				Assembler ass=new Assembler(srcProgram.getText());
				ass.assemble();
				objProgram.setText(ass.getObject().toString());
				a.setText(" ");
				x.setText(" ");
				pc.setText(" ");
				l.setText(" ");
				sw.setText(" ");
				symTable.setText(" ");
				memDump.setText(" ");
				}
				catch(Exception e)
				{
				objProgram.setText("ERROR!! "+e);
				}
		}
		});
		
		actlist openact=new actlist();
		open.addActionListener(openact);
		savelist saveact=new savelist();
		save.addActionListener(saveact);
	
		JPanel northPanel = new JPanel();
		cont.add("North",northPanel);
		JLabel northLabel=new JLabel("Register Values");
		northPanel.add(northLabel);
		JLabel emptyLabel=new JLabel("         ");
		northPanel.add(emptyLabel);
		JLabel aLabel=new JLabel("A");
		northPanel.add(aLabel);
		northPanel.add(a);
		JLabel xLabel=new JLabel("X");
		northPanel.add(xLabel);
		northPanel.add(x);
		JLabel lLabel=new JLabel("L");
		northPanel.add(lLabel);
		northPanel.add(l);
		JLabel pcLabel=new JLabel("PC");
		northPanel.add(pcLabel);
		northPanel.add(pc);
		JLabel swLabel=new JLabel("SW");
		//northPanel.add(swLabel);
		//northPanel.add(sw);
		JLabel emptyLabel1=new JLabel("               ");
		northPanel.add(emptyLabel1);
		JButton refresh = new JButton("Refresh");
		
		northPanel.add(refresh);
		
		refresh.addActionListener( new ActionListener()
		{		
			public void actionPerformed(ActionEvent e)
			{
				srcProgram.setText(" ");
				objProgram.setText(" ");
				a.setText(" ");
				x.setText(" ");
				pc.setText(" ");
				l.setText(" ");
				sw.setText(" ");
				symTable.setText(" ");
				memDump.setText(" ");
			}
		});
		
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		JLabel westLabel = new JLabel("Source SIC Program");
		westPanel.add(westLabel);
		
		JScrollPane srcPane = new JScrollPane(srcProgram);
		srcProgram.setText(openact.contents);
		westPanel.add(srcPane);
		srcProgram.setLineWrap(true);
		cont.add("West",westPanel);
	
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		JLabel eastLabel = new JLabel("Object Program");
		eastPanel.add(eastLabel);
		
		JScrollPane objPane = new JScrollPane(objProgram);
		objPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         	objPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		eastPanel.add(objPane);
		//objProgram.setLineWrap(true);
		cont.add("East",eastPanel);
	
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		JLabel centerLabel = new JLabel("Symbol Table");
		centerPanel.add(centerLabel);
		
		JScrollPane symPane = new JScrollPane(symTable);
		centerPanel.add(symPane);
		symTable.setLineWrap(true);
		cont.add("Center",centerPanel);	

		JPanel southPanel = new JPanel();
		
		cont.add("South",southPanel);
		frame.pack();
		JLabel southLabel=new JLabel("Memory Trace");
		southPanel.add(southLabel);
		
		JScrollPane memPane = new JScrollPane(memDump);
		southPanel.add(memPane);
		memDump.setLineWrap(true);
		frame.setVisible(true);
}


class actlist implements ActionListener
{
String contents=new String();
		 public void actionPerformed(ActionEvent event)
			{
			
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int result=chooser.showOpenDialog(null);
				File fex=chooser.getSelectedFile();
				Scanner myScanner = null;
				try
				{
				    myScanner = new Scanner(fex);
				    contents = myScanner.useDelimiter("\\Z").next();
				}
				catch(FileNotFoundException e)
				{
					//donothing
				}
				finally
				{
			  	  if(myScanner != null)
		    			{
					srcProgram.setText(contents);
					myScanner.close(); 
		    			}
				}
			}
}

class savelist implements ActionListener
{
String contents=new String();
		public void actionPerformed(ActionEvent event) 
		{
		String filename = new String("new.txt");
		JFileChooser savefile = new JFileChooser();
		savefile.setSelectedFile(new File(filename));
		//savefile.showSaveDialog(savefile);
		BufferedWriter writer;
		int sf = savefile.showSaveDialog(null);
		if(sf == JFileChooser.APPROVE_OPTION){
		    try {
		      writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
		       writer.write(srcProgram.getText());
		        writer.close();
		        JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
		        // true for rewrite, false for override

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}else if(sf == JFileChooser.CANCEL_OPTION){
		    JOptionPane.showMessageDialog(null, "File save has been canceled");
		}
	    }
}
}

public class UI
{
	public static void main(String[] args)
	{

	panel p=new panel();
	}
}

class MyMenuBar extends JMenuBar
{
  Color bgColor=Color.WHITE;

    public void setColor(Color color)
    {
        bgColor=color;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
}
