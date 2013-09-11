//This class creates the reader thread that reads the data from the given input file
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Reader extends Thread
{
	//voector that contains the collumn names specified in the file
	private Vector<String> collumnNames;
	
	//different data structures
	private HashTable hashtable;
	private HashList hashlist;
	private BinaryTree binarytree;
	private NaryTree narytree;
	
	//data structure data
	private String fileName;
	private boolean hashTable;
	private boolean hashList;
	private boolean binaryTree;
	private boolean naryTree;
	
	//states whether the reader is currently reading
	private static boolean currentlyReading = false;
	
	//returns the collumns names
	public Vector<String> getCollumns()
	{
		return collumnNames;
	}
	
	//unload the given data from the data structures
	public void Unload()
	{
		currentlyReading = true;

		if(hashTable)
			hashtable.clear();
		else if(hashList)
			hashlist.clear();
		else if(binaryTree)
			binarytree.clear();
		else if(naryTree)
			narytree.clear();

		System.gc();

		currentlyReading = false;
	}
	
	//returns if the reader is busy
	public static boolean isBusy()
	{
		return currentlyReading;
	}
	
	//set the default data before loading the required file
	public void setReadData(String fileName, boolean hashTable, boolean hashList, boolean binaryTree, boolean naryTree)
	{
		this.fileName = fileName;
		this.hashTable = hashTable;
		this.hashList = hashList;
		this.binaryTree = binaryTree;
		this.naryTree = naryTree;
	}
	
	//this method is used to specify a search in a data structure
	public void search(String name, String criteria, boolean searchAll, boolean searchOne)
	{

		String result = "";

		if(name.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please enter some search criteria.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			
			if(binaryTree)
			{
				if(searchAll)
					result = binarytree.searchAll(name, criteria);
				else if(searchOne)
					result = binarytree.search(name, criteria);	
			}
			else if(naryTree)
			{
				if(searchAll)
					result = narytree.searchAll(name, criteria);
				else if(searchOne)
					result = narytree.search(name, criteria);	
			}
			else if(hashList)
			{
				if(searchAll)
					result = hashlist.searchAll(name, criteria);
				else if(searchOne)
					result = hashlist.search(name, criteria);	
			}
			else if(hashTable)
			{
				if(searchAll)
					result = hashtable.searchAll(name, criteria);
				else if(searchOne)
					result = hashtable.search(name, criteria);				
			}
		
			if(result.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Sorry, search was unsuccessfull.", "Error", JOptionPane.ERROR_MESSAGE);
				GUI.addDatatoLog("Sorry, No entry with the given name existed\n");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Success, the search was successfull", "Success", JOptionPane.PLAIN_MESSAGE);
				GUI.addDatatoLog(result.trim() +"\n");
			}
		}
		
		result = "";
	}
	
	//method is used to specify a random search in structure
	public void searchRandom()
	{
		String result = "";
		
		if(binaryTree)
		{
			result = binarytree.findRandom();
		}
		else if(naryTree)
		{
			result = narytree.findRandom();
		}
		else if(hashList)
		{
			result = hashlist.findRandom();
		}
		else if(hashTable)
		{
			result = hashtable.findRandom();;
		}
		
		if(result.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Sorry, No entry was Found.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Success, the search was successfull", "Success", JOptionPane.PLAIN_MESSAGE);
			GUI.addDatatoLog(result.trim() +"\n");
		}
		
		result = "";
	}
	
	//method is used to invoke accessAll method on structure so that all entries in the structure can be visited
	public void accessAll()
	{
		if(binaryTree)
		{
			binarytree.accessAll();
		}
		else if(naryTree)
		{
			narytree.accessAll();
		}
		else if(hashList)
		{
			hashlist.accessAll();
		}
		else if(hashTable)
		{
			hashtable.accessAll();
		}
	}
	
	//mehod is used to read the given fle
	public void readFile()
	{
		//create data structures, based on structure choice
		if(hashTable)
			hashtable = new HashTable(50000);
		else if(hashList)
			hashlist = new HashList(50000);
		else if(binaryTree)
			binarytree = new BinaryTree();
		else if(naryTree)
			narytree = new NaryTree();	
		
		try
		{
			collumnNames = new Vector<String>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			
			String currentLine = reader.readLine();
			String[] linebreak = currentLine.split(",");
			
			//read in the column headers and store in vector
			String firstName = linebreak[0];
			collumnNames.add(firstName);
			String lastName = linebreak[1];
			collumnNames.add(lastName);
			String company = linebreak[2];
			collumnNames.add(company);
			String address = linebreak[3];
			collumnNames.add(address);
			String city = linebreak[4];
			collumnNames.add(city);
			String county = linebreak[5];
			collumnNames.add(county);
			String state = linebreak[6];
			collumnNames.add(state);
			String zip = linebreak[7];
			collumnNames.add(zip);
			String phone = linebreak[8];
			collumnNames.add(phone);
			String fax = linebreak[9];
			collumnNames.add(fax);
			String email = linebreak[10];
			collumnNames.add(email);
			String web = linebreak[11];
			collumnNames.add(web);
			
			//read in the rest of the data and store in appropriate data structure
			while((currentLine = reader.readLine()) != null)
			{			
				//use the comma as a split point for the line
				linebreak = currentLine.split("\",\"");
				
				//create an address book entry object
				AdressBookEntry temp = new AdressBookEntry(	linebreak[0].substring(1, linebreak[0].length()), 
															linebreak[1], 
															linebreak[2], 
															linebreak[3], 
															linebreak[4], 
															linebreak[5], 
															linebreak[6], 
															linebreak[7], 
															linebreak[8], 
															linebreak[9], 
															linebreak[10],
															linebreak[11]);
															
				//based on structure add data to that structure
				if(hashTable)
					hashtable.addToTable(temp);
				else if(hashList)
					hashlist.addToList(temp);
				else if(binaryTree)
					binarytree.addToTree(temp);
				else if(naryTree)
					narytree.addToTree(temp);
				
				//increment the current read number such that to be displayed on the GUI
				GUI.incrementReadNumber();
			}		
			
			//add information to the GUI log screen
			GUI.addDatatoLog("Load Time: "+GUI.getTimeString()+"\n");
		}
		
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	//the trhead runnabe method used to invoke reading of data
	public void run()
	{
		try
		{
			//set read data and read the file
			currentlyReading = true;
			readFile();
			currentlyReading = false;
		}
		
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
}
