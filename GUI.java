//this class creates the GUI interface that the user will be using to interact with the data structures. It also gives relative timing information
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class GUI extends Thread implements Runnable
{
	//data members
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 400;
	private static int currentNumberOfDataRead = 0;
	private static int totalNumberOfData = 350000;
	private static String fileName = "Test Data/350000.csv";
	private static String timeString;
	
	//all JFrame components
	private JFrame gui;
	private JProgressBar progress;
	
	private JButton loadContent;
	private JButton unloadContent;
	private JButton accessEachDataEntryOnce;
	private JButton search;
	private JButton searchRandomEntry;
	private JButton printLogFile;
	
	private JLabel storeinlabel;
	private JLabel searchlabel;
	
	private JLabel empty;
	private JLabel emptytwo;
	private JLabel emptythree;
	private JLabel emptyfour;
	private JLabel emptyfive;
	private JLabel emptysix;
	private JLabel emptyseven;
	
	private JTextField searchcriteria;
	
	private JRadioButton binarytree;
	private JRadioButton narytree;
	private JRadioButton hashlist;
	private JRadioButton hashtable;
	private JRadioButton searchAll;
	private JRadioButton searchOne;
	
	private JPanel loadingdatapanel;
	private JPanel searchpanel;
	private JPanel progresspanel;
	private JPanel radiobuttonpanel;
	private JPanel searchTypeButtons;
	
	private JScrollPane scrollpane;
	private static JTextArea logtextarea;
	
	private JComboBox<String> fileChooser;
	private JComboBox<String> searchTypeChooser;
	
	//booleans used to ensure that only one structure and search type can be used at a time
	private boolean binaryTreeEnabled = false;
	private boolean naryTreeEnabled = false;
	private boolean hashListEnabled = false;
	private boolean hashTableEnabled = false;
	private boolean searchAllEnabled = false;
	private boolean searchOneEnabled = false;
	
	//current reader
	private Reader reader;
	
	//timing data
	private long startTime;
	private long currentTime;	
	
	//getters and setters
	public void setBinaryTreeEnabled(boolean binaryTreeEnabled) 
	{
		this.binaryTreeEnabled = binaryTreeEnabled;
	}

	public boolean isBinaryTreeEnabled() 
	{
		return binaryTreeEnabled;
	}

	public void setNaryTreeEnabled(boolean naryTreeEnabled) 
	{
		this.naryTreeEnabled = naryTreeEnabled;
	}

	public boolean isNaryTreeEnabled() 
	{
		return naryTreeEnabled;
	}

	public void setHashListEnabled(boolean hashListEnabled) 
	{
		this.hashListEnabled = hashListEnabled;
	}

	public boolean isHashListEnabled() 
	{
		return hashListEnabled;
	}

	public void setHashTableEnabled(boolean hashTableEnabled) 
	{
		this.hashTableEnabled = hashTableEnabled;
	}

	public boolean isHashTableEnabled() 
	{
		return hashTableEnabled;
	}
	
	public static void incrementReadNumber()
	{
		currentNumberOfDataRead++;
	}

	public static void decrementReadNumber()
	{
		currentNumberOfDataRead--;
	}
	
	public static void addDatatoLog(String data)
	{
		logtextarea.append(data);
	}
	
	public static String getTimeString()
	{
		return timeString;
	}

	public boolean isSearchAllEnabled() 
	{
		return searchAllEnabled;
	}

	public void setSearchAllEnabled(boolean searchAllEnabled) 
	{
		this.searchAllEnabled = searchAllEnabled;
	}

	public boolean isSearchOneEnabled() 
	{
		return searchOneEnabled;
	}

	public void setSearchOneEnabled(boolean searchOneEnabled) 
	{
		this.searchOneEnabled = searchOneEnabled;
	}	
	//end of getters and setters
	
	//constructor
	public GUI(Reader myReader)
	{
		//set reader
		reader = myReader;
		
		//create all the GUI objects
		gui = new JFrame();
		gui.setTitle("System Information");
		gui.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		gui.setLayout(new BorderLayout());
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set the progress bar data
		progress = new JProgressBar(0,totalNumberOfData);
		progress.setValue(0);
		progress.setStringPainted(true);
		progress.setString("");
		
		//set button data as well as button action listeners
		loadContent = new JButton("Load Content");
		loadContent.addActionListener(new buttonListener("Load Content"));
		unloadContent = new JButton("Unload Content");
		unloadContent.addActionListener(new buttonListener("Unload Content"));
		accessEachDataEntryOnce = new JButton("Access Each Data Entry Once");
		accessEachDataEntryOnce.addActionListener(new buttonListener("Access Each Data Entry Once"));
		search = new JButton("Search");
		search.addActionListener(new buttonListener("Search"));
		searchRandomEntry = new JButton("Search For Random Entry");
		searchRandomEntry.addActionListener(new buttonListener("Search For Random Entry"));
		printLogFile = new JButton("Print Log File");
		printLogFile.addActionListener(new buttonListener("Print Log File"));
		
		//set label infromation
		storeinlabel = new JLabel("Store in:");
		searchlabel = new JLabel("Search for specific entry:");
		empty = new JLabel(" ");
		emptytwo = new JLabel(" ");
		emptythree = new JLabel(" ");
		emptyfour = new JLabel(" ");
		emptyfive = new JLabel(" ");
		emptysix = new JLabel(" ");
		emptyseven = new JLabel(" ");
		
		//set text field data
		searchcriteria = new JTextField(20);
	
		//create panel for radio buttons
		radiobuttonpanel = new JPanel(new GridLayout(4, 1));
		
		//set the data structure radio button data and action listeners
		binarytree = new JRadioButton("Binary Tree");
		binarytree.addActionListener(new radioButtonActionListener("BinaryTree"));
		radiobuttonpanel.add(binarytree);
		narytree = new JRadioButton("N-ary Tree");
		narytree.addActionListener(new radioButtonActionListener("NaryTree"));
		radiobuttonpanel.add(narytree);
		hashlist = new JRadioButton("Hashlist");
		hashlist.addActionListener(new radioButtonActionListener("HashList"));
		radiobuttonpanel.add(hashlist);
		hashtable = new JRadioButton("Hashtable");
		hashtable.addActionListener(new radioButtonActionListener("HashTable"));
		radiobuttonpanel.add(hashtable);
		
		//set the log text area data
		logtextarea = new JTextArea();
		logtextarea.setEnabled(false);
		logtextarea.append("=================LOG FILE=================\n\n");		
		scrollpane = new JScrollPane(logtextarea);
		scrollpane.setPreferredSize(new Dimension(450, 180));

		//set teh combo box data and create it's item listener
		String[] options = {"350000.csv", "300000.csv", "128000.csv", "61053.csv" , "31480.csv"};
		fileChooser = new JComboBox<String>(options);
		
		fileChooser.addItemListener(new ItemListener()
		{
			  public void itemStateChanged(ItemEvent ie)
			  {
				  String chosen = (String)fileChooser.getSelectedItem();
				  
				  if(chosen.equals("350000.csv"))
				  {
						totalNumberOfData = 350000;
						fileName = "Test Data/350000.csv";
						progress.setMaximum(totalNumberOfData);
						logtextarea.append(fileName+"\n");
				  }
				  
				  else if(chosen.equals("300000.csv"))
				  {
						totalNumberOfData = 300000;
						fileName = "Test Data/300000.csv";
						progress.setMaximum(totalNumberOfData);
						logtextarea.append(fileName+"\n");
				  }
				  
				  else if(chosen.equals("128000.csv"))
				  {
						totalNumberOfData = 128000;
						fileName = "Test Data/128000.csv";
						progress.setMaximum(totalNumberOfData);
						logtextarea.append(fileName+"\n");
				  }
				  
				  else if(chosen.equals("61053.csv"))
				  {
						totalNumberOfData = 61053;
						fileName = "Test Data/61053.csv";
						progress.setMaximum(totalNumberOfData);
						logtextarea.append(fileName+"\n");
				  }
				  
				  else if(chosen.equals("31480.csv"))
				  {
						totalNumberOfData = 31480;
						fileName = "Test Data/31480.csv";
						progress.setMaximum(totalNumberOfData);
						logtextarea.append(fileName+"\n");
				  }
			  }
		});
		gui.add(fileChooser, BorderLayout.NORTH);
		
		//radio buttons for searching
		searchTypeButtons = new JPanel(new FlowLayout());
		searchAll = new JRadioButton("Search All");
		searchAll.setSelected(true);
		setSearchAllEnabled(true);
		searchAll.addActionListener(new radioButtonActionListener("Search All"));
		searchOne = new JRadioButton("Search One");
		searchOne.addActionListener(new radioButtonActionListener("Search One"));
		searchTypeButtons.add(searchAll);
		searchTypeButtons.add(searchOne);
		
		//create relative panels for GUI elements
		loadingdatapanel = new JPanel(new GridBagLayout());
		searchpanel = new JPanel(new GridBagLayout());
		progresspanel =  new JPanel(new BorderLayout());
		
		//place all the objects in the correct place
		
		//========================================================
		//add the content for the search side of the GUI
		//========================================================		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		loadingdatapanel.add(loadContent, gc);
		gc.gridx = 0;
		gc.gridy = 1;
		loadingdatapanel.add(empty, gc);
		gc.gridx = 0;
		gc.gridy = 2;
		loadingdatapanel.add(storeinlabel, gc);		
		gc.gridx = 0;
		gc.gridy = 3;
		loadingdatapanel.add(emptytwo, gc);
		gc.gridx = 0;
		gc.gridy = 4;
		loadingdatapanel.add(radiobuttonpanel, gc);
		gc.gridx = 0;
		gc.gridy = 5;
		loadingdatapanel.add(emptythree, gc);		
		gc.gridx = 0;
		gc.gridy = 6;
		loadingdatapanel.add(unloadContent, gc);
		gui.add(loadingdatapanel, BorderLayout.WEST);
		//========================================================
		
		//========================================================
		//add the progress bar and log file area to the GUI
		//========================================================		
		progresspanel.add(progress, BorderLayout.CENTER);
		progresspanel.add(scrollpane, BorderLayout.SOUTH);
		
		gui.add(progresspanel, BorderLayout.SOUTH);
		//========================================================
		
		//========================================================
		//add the search elements to the GUI
		//========================================================
		String[] searchType = {"First Name","Last Name","Number"};
		searchTypeChooser = new JComboBox<String>(searchType);
		
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		searchpanel.add(accessEachDataEntryOnce, gc);
		gc.gridx = 0;
		gc.gridy = 1;
		searchpanel.add(emptyfour, gc);
		gc.gridx = 0;
		gc.gridy = 2;
		searchpanel.add(searchlabel, gc);
		gc.gridx = 0;
		gc.gridy = 3;
		searchpanel.add(searchcriteria, gc);
		gc.gridx = 1;
		gc.gridy = 3;
		searchpanel.add(emptyfive, gc);
		gc.gridx = 2;
		gc.gridy = 3; 
		searchpanel.add(searchTypeChooser, gc);
		gc.gridx = 3;
		gc.gridy = 3;
		searchpanel.add(search, gc);
		gc.gridx = 4;
		gc.gridy = 3;
		searchpanel.add(searchTypeButtons, gc);
		gc.gridx = 0;
		gc.gridy = 4;
		searchpanel.add(emptysix, gc);
		gc.gridx = 0;
		gc.gridy = 5;
		searchpanel.add(searchRandomEntry, gc);
		gc.gridx = 0;
		gc.gridy = 6;
		searchpanel.add(emptyseven, gc);
		gc.gridx = 0;
		gc.gridy = 7;
		searchpanel.add(printLogFile, gc);
		gui.add(searchpanel,  BorderLayout.CENTER);
		//========================================================
		
		//set intial selection data
		binarytree.setSelected(true);
		setBinaryTreeEnabled(true);
		
		//create GUI
		gui.pack();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
	}
	
	//method used to write the generated log file to a text file
	public void printLogFile()
	{
		//gets the text to write to a file
		String textToWrite = logtextarea.getText();
		
		try
		{
			//create printwriter that appends to a log file adding the current data
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Log Files/log.txt"), true));
			
			writer.println("======================================================");
			writer.println(new java.util.Date());
			writer.println("======================================================");
			writer.println(textToWrite);
			writer.println("======================================================");
			writer.flush();
			writer.close();

			JOptionPane.showMessageDialog(null, "Successfully appended to the log file.", "Success", JOptionPane.PLAIN_MESSAGE);
		}
		
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	//thread runner
	public void run()
	{
		while(this.isAlive())
		{
			try
			{
				progress.setValue(currentNumberOfDataRead);

				//if the reader is busy reading data
				if(Reader.isBusy())
				{
					//disable all buttons
					loadContent.setEnabled(false);
					unloadContent.setEnabled(false);
					accessEachDataEntryOnce.setEnabled(false);
					search.setEnabled(false);
					searchRandomEntry.setEnabled(false);
					printLogFile.setEnabled(false);
					
					//the current time in milliseconds
					currentTime = System.currentTimeMillis();
					
					//calculate time in minutes:seconds:milliseconds
					long seconds = ((currentTime - startTime) / 1000)%60;
					long minutes = ((currentTime - startTime) / 1000 / 60)%60;
					long milliseconds = (currentTime - startTime) %1000;
					
					if(seconds < 10)
					{
						timeString = minutes + ":0" + seconds + ":" +milliseconds;
						progress.setString("Time: " + timeString);
					}
					else
					{
						timeString = minutes + ":" + seconds + ":" +milliseconds;
						progress.setString("Time: " + timeString);
					}
				}

				else
				{
					//enable all buttons
					loadContent.setEnabled(true);
					unloadContent.setEnabled(true);
					accessEachDataEntryOnce.setEnabled(true);
					search.setEnabled(true);
					searchRandomEntry.setEnabled(true);
					printLogFile.setEnabled(true);
				}
				
				//repaint the GUI to display latest data
				gui.repaint();				
			}
			
			catch(Exception e)
			{
				System.err.println(e);
			}
		}
	}

	//the class that listens for a change in the radio buttons
	public class radioButtonActionListener implements ActionListener
	{
		//current radio button command
		String command;
		
		//constructor
		public radioButtonActionListener(String command)
		{
			this.command = command;
		}
		
		//action to perform
		@Override
		public void actionPerformed(ActionEvent a)
		{
			//based on the selected button, disable all other buttons and enable current selected one
			if(command == "BinaryTree")
			{
				binarytree.setSelected(true);
				narytree.setSelected(false);
				hashlist.setSelected(false);
				hashtable.setSelected(false);
				
				setBinaryTreeEnabled(true);
				setNaryTreeEnabled(false);
				setHashListEnabled(false);
				setHashTableEnabled(false);
			}
			
			else if(command == "NaryTree")
			{
				binarytree.setSelected(false);
				narytree.setSelected(true);
				hashlist.setSelected(false);
				hashtable.setSelected(false);
				
				setBinaryTreeEnabled(false);
				setNaryTreeEnabled(true);
				setHashListEnabled(false);
				setHashTableEnabled(false);
			}
			
			else if(command == "HashList")
			{
				binarytree.setSelected(false);
				narytree.setSelected(false);
				hashlist.setSelected(true);
				hashtable.setSelected(false);
				
				setBinaryTreeEnabled(false);
				setNaryTreeEnabled(false);
				setHashListEnabled(true);
				setHashTableEnabled(false);
			}
			
			else if(command == "HashTable")
			{
				binarytree.setSelected(false);
				narytree.setSelected(false);
				hashlist.setSelected(false);
				hashtable.setSelected(true);
				
				setBinaryTreeEnabled(false);
				setNaryTreeEnabled(false);
				setHashListEnabled(false);
				setHashTableEnabled(true);
			}
			
			else if (command == "Search All")
			{
				searchAll.setSelected(true);
				searchOne.setSelected(false);
				
				setSearchAllEnabled(true);
				setSearchOneEnabled(false);
			}
			
			else if(command == "Search One")
			{
				searchAll.setSelected(false);
				searchOne.setSelected(true);
				
				setSearchAllEnabled(false);
				setSearchOneEnabled(true);
			}
		}
	}
	
	//method that listens for a button that is clicked
	public class buttonListener implements ActionListener
	{
		//data for the current button pressed
		private String buttonPressed;
		
		public buttonListener(String button)
		{
			buttonPressed = button;
		}
		
		//action to perform
		@Override
		public void actionPerformed(ActionEvent a) 
		{
			//timing data
			long searchFinishTime;
			long searchStartTime;
			
			//if load content, add data to log file and start the reader thread
			if(buttonPressed.equals("Load Content"))
			{
				if(!Reader.isBusy())
				{
					startTime = System.currentTimeMillis();
					currentNumberOfDataRead = 0;
					reader = new Reader();
					reader.setReadData(fileName, hashTableEnabled, hashListEnabled, binaryTreeEnabled, naryTreeEnabled);
					
					if(hashTableEnabled)
						logtextarea.append("Hash Table Structure Used\n");
					else if(hashListEnabled)
						logtextarea.append("Hash List Structure Used\n");
					else if(binaryTreeEnabled)
						logtextarea.append("Binary Tree Structure Used\n");
					else
						logtextarea.append("Nary Tree Structure Used\n");
					
					reader.start();
				}
				
				else
					JOptionPane.showMessageDialog(null, "Sorry already busy loading data into the data structure.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//unload the reader content
			if(buttonPressed.equals("Unload Content"))
			{
				if(!Reader.isBusy())
				{
					startTime = System.currentTimeMillis();
					currentNumberOfDataRead = 0;
					reader.Unload();
					logtextarea.append("Unloading current data set\n");
				}

				else
					JOptionPane.showMessageDialog(null, "Sorry the system is busy unloading data from the data structure.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			//search based on the typed and selected criteria
			if(buttonPressed.equals("Search"))
			{
				//only search if reader is not busy
				if(!Reader.isBusy())
				{
					//set start and finish times and add data to the log
					searchStartTime = System.currentTimeMillis();
					logtextarea.append("Searching for: " + searchcriteria.getText() + ".\n");
					reader.search(searchcriteria.getText(), (String)searchTypeChooser.getSelectedItem(), searchAllEnabled, searchOneEnabled);
					searchFinishTime = System.currentTimeMillis();
					
					long seconds = ((searchFinishTime - searchStartTime) / 1000)%60;
					long milliseconds = (searchFinishTime - searchStartTime) %1000;
							
					logtextarea.append("Searching completed in[seconds:milliseconds]: " + seconds + ":" +milliseconds + ".\n");
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry, it appears that the system is busy.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//access al entries in the given data structure
			if(buttonPressed.equals("Access Each Data Entry Once"))
			{
				//only search if reader is not busy
				if(!Reader.isBusy())
				{
					//set start and finish times and add data to the log
					searchStartTime = System.currentTimeMillis();
					logtextarea.append("Accessing each data entry in structure once.\n");
					reader.accessAll();
					searchFinishTime = System.currentTimeMillis();
					
					long seconds = ((searchFinishTime - searchStartTime) / 1000)%60;
					long milliseconds = (searchFinishTime - searchStartTime) %1000;
							
					logtextarea.append("Searching completed in[seconds:milliseconds]: " + seconds + ":" +milliseconds + ".\n");
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry, it appears that the system is busy.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			if(buttonPressed.equals("Search For Random Entry"))
			{
				//only search if reader is not busy
				if(!Reader.isBusy())
				{
					//set start and finish times and add data to the log
					searchStartTime = System.currentTimeMillis();
					logtextarea.append("Searching for a random entry in the structure.\n");
					reader.searchRandom();
					searchFinishTime = System.currentTimeMillis();
					
					
					long seconds = ((searchFinishTime - searchStartTime) / 1000)%60;
					long milliseconds = (searchFinishTime - searchStartTime) %1000;
							
					logtextarea.append("Searching completed in[seconds:milliseconds]: " + seconds + ":" +milliseconds + ".\n");
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry, it appears that the system is busy.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//print the log text area to a file
			if(buttonPressed.equals("Print Log File"))
			{
				printLogFile();
			}
		}
	}
}
