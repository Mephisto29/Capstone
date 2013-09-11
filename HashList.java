//this represents a hash table data structure using chaining as a collision resolution method.
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class HashList extends Datastructure
{
	private ListNode[] hashlist;	//array of linked lists
	private int hashListSize;		//current array size
	private int[] listLength;		//length of list at a given point

	//constructor
	public HashList(int tableSize)
	{
		hashlist = new ListNode[tableSize];

		for(int a = 0; a < tableSize;a++)
			hashlist[a] = null;

		hashListSize = tableSize;
		listLength = new int[tableSize];
		for(int i = 0 ; i < tableSize ; i ++)
			listLength[i] = 0;
	}

	//add data to the head of the linked list at the given point in the array
	public void addToList(AdressBookEntry dataToAdd)
	{
		//get the position in the array
		int getPositionInList = getPositionInTable(dataToAdd.getFirstName().toUpperCase());

		//if null create new linked list at the point and add data the the list
		if(hashlist[getPositionInList] == null)
		{
			hashlist[getPositionInList] = new ListNode(dataToAdd, null);
			listLength[getPositionInList]++;
		}
		//if not null, add data to the head of the list
		else
		{
			hashlist[getPositionInList] = new ListNode(dataToAdd, hashlist[getPositionInList]);
			listLength[getPositionInList]++;
		}
	}

	//search and return after finding first piece of data that matches search and criteria
	public String search(String name, String field)
	{
		//current linked list and the result to returns
		String result = "";
		ListNode newNode = null;

		//if field is the first name
		if(field.equals("First Name"))
		{
			int getPositionInList = getPositionInTable(name.toUpperCase());

			if(hashlist[getPositionInList] != null)
			{
				newNode = hashlist[getPositionInList];

				//loop until the current node is not null
				while(newNode != null)
				{
					if(newNode.getData().getFirstName().toUpperCase().equals(name.toUpperCase()))
					{
						result = newNode.getData() +"\n";
						return result;
					}

					newNode = newNode.getNext();
				}
			}
			else
			{
				return result;
			}
		}
		else
		{
			for(int i = 0 ; i < hashListSize ; i ++)
			{
				if(hashlist[i] != null)
				{
					newNode = hashlist[i];

					//loop until the current node is not null
					while(newNode != null)
					{
						//if field is the last name
						if(field.equals("Last Name"))
						{
							if(newNode.getData().getLastName().toUpperCase().equals(name.toUpperCase()))
							{
								result = newNode.getData() +"\n";
								return result;
							}
						}
						//if field is the Number
						else if(field.equals("Number"))
						{
							if(newNode.getData().getPhone().toUpperCase().equals(name.toUpperCase()))
							{
								result = newNode.getData() +"\n";
								return result;
							}
						}

						newNode = newNode.getNext();
					}
				}
			}
		}

		return result;
	}
	
	//method used to search for all data in the table matching a specific search
	public String searchAll(String name, String field)
	{
		//the result and linked list at a given point
		String result = "";
		ListNode newNode = null;

		//if field is the first name
		if(field.equals("First Name"))
		{
			//get position in list
			int getPositionInList = getPositionInTable(name.toUpperCase());

			//if not null check agains criteria match any case
			if(hashlist[getPositionInList] != null)
			{
				newNode = hashlist[getPositionInList];

				//loop until current node is null
				while(newNode != null)
				{
					if(newNode.getData().getFirstName().toUpperCase().equals(name.toUpperCase()))
					{
						result = result + newNode.getData() +"\n";
					}

					newNode = newNode.getNext();
				}
			}
			else
			{
				return result;
			}
		}
		else
		{
			//loop from start to finsih
			for(int i = 0 ; i < hashListSize ; i ++)
			{
				//if current position is not null
				if(hashlist[i] != null)
				{
					newNode = hashlist[i];

					while(newNode != null)
					{
						//if field is the last name
						if(field.equals("Last Name"))
						{
							if(newNode.getData().getLastName().toUpperCase().equals(name.toUpperCase()))
							{
								result = result + newNode.getData() +"\n";
							}
						}
						//if field is the number
						else if(field.equals("Number"))
						{
							if(newNode.getData().getPhone().toUpperCase().equals(name.toUpperCase()))
							{
								result = result + newNode.getData() +"\n";
							}
						}

						newNode = newNode.getNext();
					}
				}
			}
		}

		return result;
	}

	//method used to get a random value in the table
	public String findRandom()
	{
		//the result with the linked list at the random position in the table
		String result = "";
		ListNode newNode = null;
		Random random = new Random();
		int position = random.nextInt(hashListSize);
		int positionInList = 0;
		boolean initialised = false;

		//while the data is not initialized
		while(!initialised)
		{
			if(hashlist[position]!= null)
			{
				positionInList = random.nextInt(listLength[position]);
				initialised = true;
			}
			else
				position = random.nextInt(hashListSize);
		}

		//loop over linked list in the random position in table to get the random data
		for(int i = 0 ; i < positionInList;i++)
			newNode = hashlist[position].getNext();

		if(positionInList > 0)
			result = newNode.getData().toString();
		else
			result = hashlist[position].getData().toString();

		//return the result
		return result;
	}

	//method used to access all data in the table
	public void accessAll()
	{
		//current linked list
		ListNode newNode = null;
		int accessed = 0;
		//loop over all data in the table
		for(int i = 0 ; i < hashListSize ; i ++)
		{
			//if linked list at given point is not null continue
			if(hashlist[i]!=null)
			{
				//set linked list and iterate over the list size
				newNode = hashlist[i];
				for(int j = 0 ; j < listLength[i] ; j ++)
				{
					if(newNode.getData().getAccessed())
					{
						newNode.getData().setAccessed(false);
						accessed++;
					}
					else
					{
						newNode.getData().setAccessed(true);
						accessed++;
					}
					newNode = newNode.getNext();

				}
			}

		}
		JOptionPane.showMessageDialog(null, "Accessed all entries in the HashList, amounting to : " + accessed, "Success", JOptionPane.PLAIN_MESSAGE);
	}

	//use a hash function to get the position in the table for a given string
	public int getPositionInTable(String name)
	{
		int hashValue = 0;

		for(int a = 0;a < name.length();a++)
			hashValue = 37 * hashValue + name.hashCode(); 

		//return a value iun range of 0 and table size
		return Math.abs(hashValue) % hashListSize;
	}

	//method used to disply the current table of linked lists
	public void displayHashList()
	{
		//loop from start to finish
		for(int a = 0;a < hashListSize;a++)
		{
			//if not null loop and display all nodes in the list
			if(hashlist[a] != null)
			{
				ListNode currentNode = hashlist[a];
				
				//loop until the current node is null
				while(currentNode != null)
				{
					System.out.println(currentNode.getData());
					currentNode = currentNode.getNext();
				}
			}
		}
	}

	//clear all data by nulifying all data in the ist
	public void clear()
	{
		for(int i = 0; i < hashListSize;i++)
		{
			if(hashlist[i] != null)
			{
				ListNode currentList = hashlist[i];
				listLength[i] = 0;

				while(currentList != null)
				{
					currentList.clear();
					currentList = currentList.getNext();
					GUI.decrementReadNumber();
				}
			}
		}
	}
}
