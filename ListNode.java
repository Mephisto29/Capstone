//This class represents a node in a linked list in the hash list
public class ListNode 
{
	//current data of this node with the links to the next and previous nodes
	private AdressBookEntry data;
	private ListNode next;
	private ListNode previous;
	
	//getters and setters
	public void setData(AdressBookEntry data) 
	{
		this.data = data;
	}

	public AdressBookEntry getData() 
	{
		return data;
	}

	public void setNext(ListNode next) 
	{
		this.next = next;
	}

	public ListNode getNext() 
	{
		return next;
	}

	public void setPrevious(ListNode previous) 
	{
		this.previous = previous;
	}

	public ListNode getPrevious() 
	{
		return previous;
	}
	//end of getters and setters
	
	//constructor
	public ListNode(AdressBookEntry data, ListNode next)
	{
		this.setData(data);
		this.next = next;
	}

	//clear method used to nullify all objects
	public void clear()
	{
		data = null;
		previous = null;
	}
}
