import java.lang.ref.WeakReference;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
public class BinaryTree
{

	// Variables that will be used throughout the Program
	// these will store the data that will be manipulated
	private Node rootNode;
	private Node newNode;
	private Node currentNode;
	private boolean found;
	private int treeSize;
	private int accessed;
	private String searchResult;

	// This is the constructor, that initialises the relevant variables
	public BinaryTree()
	{
		rootNode = null;
		newNode = null;
		currentNode = null;
		found = false;
		treeSize = 0;
		accessed = 0;
		searchResult = "";
	}

	// This method returns the size of the Binary Tree
	public int getTreeSize()
	{
		return treeSize;
	}

	// this method recursively adds nodes to the tree
	public void addToTree(AdressBookEntry dataToAdd)
	{

		// newNode is used to create a node containing the relevant data
		newNode = new Node(dataToAdd);

		// This check checks that there exists a root node
		// If none exists, one is created.
		if(treeSize < 1)
		{
			rootNode = newNode;
			rootNode.setParent(null);
			currentNode = rootNode;
			treeSize++;
		}
		// In the event that the root has not been created
		else
		{
			// If the newNode's data is greater than that of the current Node 
			// the newNode will be added somewhere to the right of the current Node
			if(currentNode.getData().getFirstName().compareTo(newNode.getData().getFirstName()) > 0)
			{
				// If the currentNode has a Right node, the method is recalled with
				// the currentNode set to that of the Right hand side node of the current currentNode 
				if(currentNode.hasRight())
				{
					currentNode = currentNode.getRight();
					addToTree(dataToAdd);
				}
				//  If the currentNode does not have a right node, 
				//  the newNode is added to the right hand side immediately
				else
				{
					newNode.setParent(currentNode);
					currentNode.setRight(newNode);
					treeSize++;
				}
			}
			// If the newnode data is equivalent to that of the currentNode
			// the data is directly added to the left hand side of the currentNode
			// and the currentNode's left branch is repositioned to that of the newNode.
			else if(currentNode.getData().getFirstName().compareTo(newNode.getData().getFirstName()) == 0)
			{
				if(currentNode.hasLeft())
				{
					newNode.setParent(currentNode);
					newNode.setLeft(currentNode.getLeft());
					currentNode.setLeft(newNode);
				}
				else
				{
					newNode.setParent(currentNode);
					currentNode.setLeft(newNode);
				}
				treeSize++;
			}
			//  If it is neither equal or more than then the newNode will be added to the left
			else
			{
				if(currentNode.hasLeft())
				{
					currentNode = currentNode.getLeft();
					addToTree(dataToAdd);
				}
				else
				{
					newNode.setParent(currentNode);
					currentNode.setLeft(newNode);
					treeSize++;
				}
			}

		}
		// Once added, set the currentNode to the rootNode for future additions.
		currentNode = rootNode;
	}

	// This method finds a rondom entry in the data structure by producing a random number.
	// It then traverses the data structure as far as possible with regrads to the number.
	// Once it cannot traverse any more, or it has traversed as many times as the random number specified,
	// the method returns the data at that position.
	public String findRandom()
	{
		// string to return results with
		String result = "";
		// randomiser for finding a random number
		Random random = new Random();

		// a random entry can only be found if there exists a tree
		if(treeSize>0)
		{
			// this number represents the number of traversals to be executed
			int downs = random.nextInt(treeSize);
			// this number determin es whether or not to traverse left or right
			int direction = downs%2;
			Node newNode = rootNode;

			// if the number of traversals are more than 0 enter the statment
			// otherwise return the data contained in the root node
			if(downs > 0)
			{
				// iterate through the tree until a leaf is reached or the specified 
				// traversals have been traversed
				for(int i = 0 ; i < downs ; i ++)
				{
					// this produces a value of either 0 or 1. to specify left or right
					direction = random.nextInt(treeSize)%2;
					if(direction == 0)
					{
						if(newNode.hasRight())
							newNode = newNode.getRight();
						else
						{
							if(newNode.hasLeft())
								newNode = newNode.getLeft();
							else
							{
								result = newNode.getData().toString();
								break;
							}
						}
					}
					else
					{
						if(newNode.hasLeft())
							newNode = newNode.getLeft();
						else
						{
							if(newNode.hasRight())
								newNode = newNode.getRight();
							else
							{
								result = newNode.getData().toString();
								break;
							}
						}
					}
				}
			}
			else
				result = newNode.getData().toString();
		}

		// return the result if one was found
		return result;
	}

	// This method accesses each entry once, byt changing a boolean value that is stored within the data.
	// This ensures that the data is actually accessed, and not just assumed to be.
	public void accessAll()
	{
		if(treeSize > 0)
		{
			Node newNode = rootNode;
			
			AccessAll(newNode);
			JOptionPane.showMessageDialog(null, "Accessed all entries in the Binary Tree, amounting to : " + accessed, "Success", JOptionPane.PLAIN_MESSAGE);
			accessed = 0;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "There are no entries in the tree to be searched, Sorry.", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	// this method works in tandum to that of the accessALL() method
	public void AccessAll(Node newNode)
	{
		if(newNode.hasLeft())
		{
			AccessAll(newNode.getLeft());
		}
		if(newNode.hasRight())
		{
			AccessAll(newNode.getRight());
		}
		if(newNode.getData().getAccessed())
			newNode.getData().setAccessed(false);
		else
			newNode.getData().setAccessed(true);

		accessed++;
	}
	
	// This method searches the data structure for a certain string.
	public String search(String dataToFind, String field)
	{
		// string to store results in and to return
		searchResult = "";
		// Calls the actual method that does the searching
		searchResult = findInTree(dataToFind, field, currentNode);
		// reset the current Node to the rootNode.
		currentNode = rootNode;
		found = false;
		// return the search results
		return searchResult;
	}
	
	// Similar to that of the search method, however this method
	// returns all the occurences of the specified data.
	public String searchAll(String dataToFind, String field)
	{
		searchResult = "";
		searchResult = findAllInTree(dataToFind, field, currentNode);
		currentNode = rootNode;
		found = false;
		return searchResult;
	}

	// the method that traverses the tree and searches for the specified data
	public String findInTree(String dataToFind, String field, Node currentNode)
	{
		String result = "";
		
		// These checks are to determine which part of the data to search for,
		// First Name the entry with wich the tree was created
		if(field.equals("First Name"))
		{
			// checks if the currentNode's data corresponds to that of the searched for data
			if(currentNode.getData().getFirstName().toUpperCase().equals(dataToFind.toUpperCase()))
			{
				// adds the located data to the result string
				result = result + currentNode.getData().toString() + "\n";
				found = true;
				// since only one entry is being searched for, the method returns the discovered result
				return result;
			}
			else
			{
				// to ensure that the program does not enter this area unnesisarily, it first checks that
				// previous calls have not found anything yet 
				if(!found)
				{
					// These checks determine which direction to traverse based on the currentNode and the searched for data
					if(currentNode.getData().getFirstName().toUpperCase().compareTo(dataToFind.toUpperCase()) > 0)
					{
						if(currentNode.hasRight())
						{
							result = result + findInTree(dataToFind, field, currentNode.getRight());
							if(!result.equals(""))
							{
								return result;
							}
						}
					}
					else
					{
						if(currentNode.hasLeft())
						{
							result = result + findInTree(dataToFind, field, currentNode.getLeft());
							if(!result.equals(""))
								return result;
						}
					}
				}
			}
		}
		else
		{
			// The following checks are similar to that of the above, however the search cannot be directed as
			// the entries being searched are not ordered with regards to the type being searched for
			if(field.equals("Last Name"))
			{
				if(currentNode.getData().getLastName().toUpperCase().equals(dataToFind.toUpperCase()))
				{
					result = result + currentNode.getData().toString() + "\n";
					found = true;
					return result;
				}
				else if(found != true)
				{
					if(currentNode.hasLeft())
					{
						result = result + findInTree(dataToFind, field, currentNode.getLeft());
						if(!result.equals(""))
						{
							found = true;
							return result;
						}
					}
					if(currentNode.hasRight())
					{
						result = result + findInTree(dataToFind, field, currentNode.getRight());
						if(!result.equals(""))
						{
							found = true;
							return result;
						}
					}
				}
			}
			else if(field.equals("Number"))
			{
				if(currentNode.getData().getPhone().toUpperCase().equals(dataToFind.toUpperCase()))
				{
					result = result + currentNode.getData().toString() + "\n";
					found = true;
					return result;
				}
				else if(found != true)
				{
					if(currentNode.hasLeft())
					{
						result = result + findInTree(dataToFind, field, currentNode.getLeft());
						if(!result.equals(""))
							return result;
					}
					if(currentNode.hasRight())
					{
						result = result + findInTree(dataToFind, field, currentNode.getRight());
						if(!result.equals(""))
							return result;
					}
				}
			}
		}
		return result;
	}
	
	// The following method is exactly the same as the previous method, 
	// only it produces all the found results equal to that of the searched criteria
	// instead of stopping once one entry is found
	public String findAllInTree(String dataToFind, String field, Node currentNode)
	{
		String result = "";
		if(field.equals("First Name"))
		{
			if(dataToFind.toUpperCase().equals(currentNode.getData().getFirstName().toUpperCase()))
			{
				result = result + currentNode.getData().toString() + "\n";
				found = true;

				if(currentNode.hasLeft())
				{
					if(currentNode.getLeft().getData().getFirstName().toUpperCase().equals(dataToFind.toUpperCase()))
						result = result + findAllInTree(dataToFind, field, currentNode.getLeft());
				}

				if(currentNode.hasRight())
				{
					if(currentNode.getRight().getData().getFirstName().toUpperCase().equals(dataToFind.toUpperCase()))
						result = result + findAllInTree(dataToFind, field, currentNode.getRight());
				}
			}
			else
			{
				if(!found)
				{
					if(currentNode.getData().getFirstName().toUpperCase().compareTo(dataToFind.toUpperCase()) > 0)
					{
						if(currentNode.hasRight())
							result = result + findInTree(dataToFind, field, currentNode.getRight());
					}
					else
					{
						if(currentNode.hasLeft())
							result = result + findInTree(dataToFind, field, currentNode.getLeft());
					}
				}
			}
		}
		else
		{
			if(field.equals("Last Name"))
			{
				if(currentNode.getData().getLastName().toUpperCase().equals(dataToFind.toUpperCase()))
				{
					result = result + currentNode.getData().toString() + "\n";
				}
				else
				{
					if(currentNode.hasLeft())
					{
						result = result + findAllInTree(dataToFind, field, currentNode.getLeft());
					}
					if(currentNode.hasRight())
					{
						result = result + findAllInTree(dataToFind, field, currentNode.getRight());
					}
				}
			}
			else if(field.equals("Number"))
			{
				if(currentNode.getData().getPhone().toUpperCase().equals(dataToFind.toUpperCase()))
				{
					result = result + currentNode.getData().toString() + "\n";
				}
				else
				{
					if(currentNode.hasLeft())
					{
						findAllInTree(dataToFind, field, currentNode.getLeft());
					}
					if(currentNode.hasRight())
					{
						findAllInTree(dataToFind, field, currentNode.getRight());
					}
				}
			}
		}
		return result;
	}
	
	// This Method destroys the data structure and clears the used memory
	// This method forces garbage collection.
	public void clear()
	{
		rootNode = null;
		Object obj = new Object();
		WeakReference<Object> ref = new WeakReference<Object>(obj);
		obj = null;
		while(ref.get() != null) 
		{
			System.gc();
		}
	}

	// This was used to determine wether or not the tree was being constructed correctly
	public void displayTree()
	{
		if(currentNode != null)
			drawTree(currentNode);
		else
			JOptionPane.showMessageDialog(null, "No Tree To Draw, Sorry.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	//  Method that draws the data of the tree at the currentnode
	public void drawTree(Node currentNode)
	{
		if(currentNode.hasLeft())
			drawTree(currentNode.getLeft());
		if(currentNode.hasRight())
			drawTree(currentNode.getRight());
		System.out.println(currentNode.getData().toString());
	}
}
