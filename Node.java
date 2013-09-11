import java.util.Vector;

public class Node
{
	private boolean isRoot = false;
	private boolean hasLeft = false;
	private boolean hasRight = false;
	private AdressBookEntry data;
	private Node parent;
	private Node left;
	private Node right;
	private char letter;
	private String letterList = "";
	
	private int children;
	private Vector<Node> offspring = new Vector<Node>();
	
	public void setData(AdressBookEntry data) 
	{
		this.data = data;
	}
	
	public void setLetter(char letter)
	{
		this.letter = letter;
	}
	
	public char getLetter()
	{
		return letter;
	}
	
	public void setLetterList(char letter)
	{
		letterList += letter;
	}
	
	public String getLetterList()
	{
		return letterList;
	}
	
	public Node getChild(char letter)
	{
		Node child = null;
		for(int i = 0 ; i < children; i++)
		{
			if(offspring.elementAt(i).getLetter() == letter)
				child = offspring.elementAt(i);
		}
		return child;
	}
	
	public Node getChild(int number)
	{
		return offspring.elementAt(number);
	}
	
	public int getChildren()
	{
		return children;
	}
	
	public void addChild(Node child)
	{
		offspring.add(child);
		children++;
		setLetterList(child.getLetter());
	}
	
	public Node getParent() 
	{
		return parent;
	}

	public void setParent(Node parent) 
	{
		this.parent = parent;
	}

	public AdressBookEntry getData() 
	{
		return data;
	}

	public void setLeft(Node left) 
	{
		this.left = left;
		this.hasLeft = true;
		children++;
	}

	public Node getLeft() 
	{
		return left;
	}

	public void setRight(Node right) 
	{
		this.right = right;
		this.hasRight = true;
		children++;
	}

	public Node getRight() 
	{
		return right;
	}

	public boolean isRoot() 
	{
		return isRoot;
	}

	public void setRoot(boolean isRoot) 
	{
		this.isRoot = isRoot;
	}
	
	public boolean hasLeft()
	{
		return hasLeft;
	}
	public boolean hasRight()
	{
		return hasRight;
	}
	public void setHasLeft(boolean option)
	{
		hasLeft = option;
	}
	
	public void setHasRight(boolean option)
	{
		hasRight = option;
	}
	
	
	public Node(AdressBookEntry data)
	{
		this.setData(data);
	}
	
	public Node(char letter)
	{
		this.setLetter(letter);
	}
	
	public Node()
	{}
}
