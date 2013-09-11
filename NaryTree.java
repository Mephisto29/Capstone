import java.lang.ref.WeakReference;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class NaryTree
{
	// Variables to be used throughout the program to contain the data
	// that will be manipulated
	private Node rootNode;
	private Node newNode;
	private Node currentNode;
	private boolean found;
	private int treeSize;
	private int accessed;

	// This constructor initialises all the variables to be used
	public NaryTree()
	{
		rootNode = new Node();
		rootNode.setLetter(' ');
		newNode = null;
		currentNode = null;
		found = false;
		treeSize = 0;
	}

	// This method returns the tree size
	public int getTreeSize()
	{
		return treeSize;
	}

	// This method adds nodes to the tree
	public void addToTree(AdressBookEntry dataToAdd)
	{
		// creates a newNode to store the data in
		newNode = new Node(dataToAdd);
		// initializes the letter of the letter element of the node
		newNode.setLetter('.');

		// If there exists no root node yet, create one
		if(treeSize < 1)
		{
			rootNode.setParent(null);
			currentNode = rootNode;
			treeSize++;
		}
		// If there exists a root node, add to it
		else
		{
			// variable to be used for the iterator
			int length = dataToAdd.getFirstName().length();

			// loop through the building criteria, and create nodes as needed
			for(int i = 0 ; i < length ; i++)
			{
				// for every letter of the new Entry to add, traverse the tree through the letter nodes if they exist
				// if they do not, create them
				if(charContained(currentNode.getLetterList().toUpperCase(), dataToAdd.getFirstName().toUpperCase().charAt(i)))
				{
					currentNode = currentNode.getChild(dataToAdd.getFirstName().toUpperCase().charAt(i));
				}
				else
				{
					// creates a child to the current Node that represents the character at i
					Node child = new Node(dataToAdd.getFirstName().toUpperCase().charAt(i));
					child.setParent(currentNode);
					currentNode.addChild(child);
					currentNode = child;
					treeSize++;
				}
			}
			// once all the leter Nodes have been traversed, the current Position is where the newNode should be added.
			// so the new Node is added to the end of the last letter of the word.
			newNode.setParent(currentNode);
			currentNode.addChild(newNode);
			treeSize++;
		}
		currentNode = rootNode;
	}

	// This method checks to see if a string contains a particular character
	public boolean charContained(String string, char letter)
	{
		for(int i = 0; i < string.length() ; i++)
		{
			if(string.charAt(i) == letter)
				return true;
		}
		return false;
	}

	// This method is called to initiate a search for a specific entry
	public String search(String dataToFind, String field)
	{
		// string to which the resulting data is added
		String searchResult = "";

		// calling of the actual search method
		searchResult = findInTree( dataToFind,  field, currentNode);

		// reset the current Node to the root node for future searches
		currentNode = rootNode;
		// return the results
		return searchResult;
	}

	public String findInTree(String dataToFind, String field, Node currentNode)
	{
		// string to wich to append results
		String searchResult = "";

		// Checks which field needs to be checked, where First Name is the field to which the Tree was constructed.
		if(field.equals("First Name"))
		{
			int length = dataToFind.length();

			// Loop while the entry has not been found
			while(!found)
			{
				try
				{
					// loop through the letters and traverse through the relevant letters if they exist.
					for(int i = 0 ; i < length; i++)
					{
						if(charContained(currentNode.getLetterList().toUpperCase(), dataToFind.toUpperCase().charAt(i)))
							currentNode = currentNode.getChild(dataToFind.toUpperCase().charAt(i));
						else
						{
							return searchResult;
						}
					}
					// Loop through the children of the final letter as the searched for element should be one of the chilren
					for(int a = 0 ; a < currentNode.getChildren(); a ++)
					{
						if(currentNode.getChild(a).getData() != null)
						{
							if(currentNode.getChild(a).getData().getFirstName().toUpperCase().equals(dataToFind.toUpperCase()))
							{
								// adds the search result to the string to be returned, and returns the result.
								searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
								return searchResult;
							}
						}
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "An error occured at N-ary Tree search : \n " + e, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();

					return searchResult;
				}
			}
		}
		// If the search field is not First Name, do an extensive search fo the entire tree  to find the relevant data.
		// this occurs as the data to be found can be any where in the tree
		else
		{
			while(!found)
			{
				try
				{
					if(currentNode.getChildren() == 0)
					{
						break;
					}
					else
					{
						// loops though every child of every node to search for criteria
						for(int a = 0 ; a < currentNode.getChildren(); a ++)
						{
							if(currentNode.getChild(a).getData() != null)
							{
								if(field.equals("Last Name"))
								{
									// check the specified field of the data entry
									if(currentNode.getChild(a).getData().getLastName().toUpperCase().equals(dataToFind.toUpperCase()))
									{
										searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
										return searchResult;
									}
								}
								else if(field.equals("Number"))
								{
									// check the specified field of the data entry
									if(currentNode.getChild(a).getData().getPhone().toUpperCase().equals(dataToFind.toUpperCase()))
									{
										searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
										return searchResult;
									}
								}
							}
							else
							{
								// if the en try has not been found yet, recursivelty search the tree again, 
								// with a new node as the initail point of entry.
								searchResult = searchResult + findInTree(dataToFind, field, currentNode.getChild(a));
								if(!searchResult.equals(""))
								{
									return searchResult;
								}
							}
						}
						break;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "An error occured at N-ary Tree search : \n " + e, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();

					return searchResult;
				}
			}
		}

		currentNode = rootNode;
		return searchResult;
	}
	
	// This method is called to initialise a search for all the entries equavelent to that of the search criteria 
	public String searchAll(String dataToFind, String field)
	{
		String searchResult = "";
		
		searchResult = findAllInTree(dataToFind,field, currentNode);
		currentNode = rootNode;
		
		return searchResult;
	}

	// This method is the same as the normal search method, only that it returns all the entries in the entire tree
	// that match the specified search criteria.
	// Which for certain fields mean that the entire tree needs to be traversed
	public String findAllInTree(String dataToFind, String field, Node currentNode)
	{
		String searchResult = "";

		if(field.equals("First Name"))
		{
			int length = dataToFind.length();

			while(!found)
			{
				try
				{
					for(int i = 0 ; i < length; i++)
					{
						if(charContained(currentNode.getLetterList().toUpperCase(), dataToFind.toUpperCase().charAt(i)))
							currentNode = currentNode.getChild(dataToFind.toUpperCase().charAt(i));
						else
						{
							return searchResult;
						}
					}
					for(int a = 0 ; a < currentNode.getChildren(); a ++)
					{
						if(currentNode.getChild(a).getData() != null)
						{
							if(currentNode.getChild(a).getData().getFirstName().toUpperCase().equals(dataToFind.toUpperCase()))
							{
								searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
							}
						}
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "An error occured at N-ary Tree search : \n " + e, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();

					return searchResult;
				}
			}
		}
		else
		{
			while(!found)
			{
				try
				{
					if(currentNode.getChildren() == 0)
					{
						break;
					}
					else
					{
						for(int a = 0 ; a < currentNode.getChildren(); a ++)
						{
							if(currentNode.getChild(a).getData() != null)
							{
								if(field.equals("Last Name"))
								{
									if(currentNode.getChild(a).getData().getLastName().toUpperCase().equals(dataToFind.toUpperCase()))
									{
										searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
									}
								}
								else if(field.equals("Number"))
								{
									if(currentNode.getChild(a).getData().getPhone().toUpperCase().equals(dataToFind.toUpperCase()))
									{
										searchResult = searchResult + currentNode.getChild(a).getData().toString() + "\n";
									}
								}
							}
							else
							{
								searchResult = searchResult + findAllInTree(dataToFind, field, currentNode.getChild(a));
							}
						}
						break;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "An error occured at N-ary Tree search : \n " + e, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();

					return searchResult;
				}
			}
		}

		currentNode = rootNode;
		return searchResult;
	}

	// This method returns a random entry within the tree
	public String findRandom()
	{
		// string to add result to
		String result = "";
		// Randomizer for getting random numbers
		Random random = new Random();
		if(treeSize>0)
		{
			// gets a random number representing the number of traversals to be taken
			int downs = random.nextInt(treeSize);
			int child = rootNode.getChildren();
			int direction = 0;
			Node newNode = rootNode;

			// only if the amount of recursions are more than 0 enter the statement
			// other wise recall the method, as the root node should not contain any relevant data
			if(downs > 0)
			{
				// generates a random number based on the number of children a node has
				child = newNode.getChildren();
				direction = random.nextInt(child);
				// loop for the specified number of traversals
				for(int i = 0 ; i < downs ; i ++)
				{
					child = newNode.getChildren();


					if(child > 0)
					{
						//if there exist children, go to them
						direction = random.nextInt(child);
						newNode = newNode.getChild(direction);
					}
					else
					{
						// if no children exist, we are at a leaf node, which means that there is
						// relevant data, and traversal canot be continued, so return the data in the Node
						result = result + newNode.getData().toString() + "\n";
						break;
					}
				}
			}
			else
				result = findRandom();
		}

		return result;
	}

	// This method accesses All the elements in the Tree
	public void accessAll()
	{
		
		if(treeSize > 0)
		{
			Node newNode = rootNode;
			// calls recursive method with initial note set to the root node
			AccessAll(newNode);
			JOptionPane.showMessageDialog(null, "Accessed all entries in the N-ary Tree, amounting to : " + accessed, "Success", JOptionPane.PLAIN_MESSAGE);
			System.out.println("Accessed all entries " + accessed);
			accessed = 0;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "There were No entries in the Tree to access", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	// this method does the recursive calls to effectively access all the nodes,
	// it enters a data entry, and changes a variable boolean, to make sure that each entry is, in fact, accessed.
	public void AccessAll(Node newNode)
	{
		if(newNode.getChildren() > 0)
		{
			for(int i = 0 ; i < newNode.getChildren(); i++)
				AccessAll(newNode.getChild(i));
		}
		else
		{
			if(newNode.getData().getAccessed())
				newNode.getData().setAccessed(false);
			else
				newNode.getData().setAccessed(true);

			accessed++;
		}
	}
	
	
	// This Method destroys the data structure and clears the used memory
	// This method forces garbage collection.
	public void clear()
	{
		rootNode = null;
		Object obj = new Object();
		WeakReference ref = new WeakReference<Object>(obj);
		obj = null;
		while(ref.get() != null) 
		{
			System.gc();
		}
	}

	// The code was used to debug the tree, and confirm that the tree is generated correctly
	public void displayTree()
	{
		if(currentNode != null)
			drawTree(currentNode);
		else
			System.out.println("No Tree To Draw, Sorry");
	}

	public void drawTree(Node currentNode)
	{
		for(int i = 0 ; i < currentNode.getChildren() ; i ++)
		{
			drawTree(currentNode.getChild(i));
		}
		if(currentNode.getData() != null)
			System.out.println(currentNode.getData().toString());
		else;
	}
}
