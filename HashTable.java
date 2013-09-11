//this class is used to represent a Hash Table data structure, it stores data into an array based on a hash function and 
//resizes the data array if the loadfactor is larger than a predefined size
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class HashTable extends Datastructure
{
	private AdressBookEntry[] hashtable;	//hash table array
	private int hashTableSize;				//current hash table size
	private int currentNumberOfEntries;		//the total number of entries
	private float loadFactor;				//current load factor

	//constructor
	public HashTable(int tableSize)
	{
		hashTableSize = tableSize;
		loadFactor = 0;
		hashtable = new AdressBookEntry[hashTableSize];
	}

	//add data to the table
	public void addToTable(AdressBookEntry newEntry)
	{
		//get the position based in the new enter first name
		int positionInTable = getHashFunction(newEntry.getFirstName());

		//if the retrieved position is null, then add data to that point in the table
		if(hashtable[positionInTable] == null)
		{
			hashtable[positionInTable] = newEntry;
			currentNumberOfEntries++;
		}

		//if the data is not null, then a collision occurs, thus use quadratic probing to resolve the collisions
		else
		{
			//get new position in table
			positionInTable = resolveCollision(positionInTable);

			//if the returned value is not -1 then add data to table
			if(positionInTable != -1)
			{
				hashtable[positionInTable] = newEntry;
				currentNumberOfEntries++;
			}

			else
				System.out.println("No hash position: " + loadFactor);
		}

		//compute load factor
		loadFactor =  (float)currentNumberOfEntries/hashTableSize;

		//resize the table if factor is larger that 50%
		if(loadFactor >= 0.5)
			resize();
	}

	//method used to get the hash funtion for a given string
	public int getHashFunction(String addressBookEntryName)
	{
		int hashValue = addressBookEntryName.hashCode();

		return Math.abs(hashValue) % hashTableSize;
	}

	//this method uses quadratic probing to get the next available position in the table
	public int resolveCollision(int currentPosition)
	{
		//current offset
		 int offset = 1;
		 //current posision in the tabel
	     int currentPos = currentPosition;
	     
		 //continue probing until current position returns null value
	     while(hashtable[ currentPos ] != null)
	     {
	    	 currentPos += offset;
	         offset += 2;
	            
	         if( currentPos >= hashtable.length )
	            currentPos -= hashtable.length;
	     }

		return currentPos;
	}

	//resize the table to twice its current size
	public void resize()
	{
		//allocate temp array to current array and create new array with double current array size
		AdressBookEntry[] temp = hashtable;
		hashTableSize *= 2;
		hashtable = new AdressBookEntry[hashTableSize];

		//add existing data to the table
		for(int i = 0;i < hashtable.length;i++)
		{
			if(i < temp.length)
				hashtable[i] = temp[i];
			else
				hashtable[i] = null;
		}

		//compute the load factor
		loadFactor = (float)currentNumberOfEntries/hashTableSize;
	}

	//method used to display the current table
	public void displayTable()
	{
		//loop and display if current value is not null
		for(int i = 0; i < hashTableSize;i++)
			if(hashtable[i] != null)
				System.out.println(hashtable[i]);
	}

	//nullify all data in the table
	public void clear()
	{
		for(int i = 0;i < hashtable.length;i++)
		{
			hashtable[i] = null;
			GUI.decrementReadNumber();
		}
	}

	//search for all data in table based on given string and criteria
	public String searchAll(String name, String criteria)
	{
		String result = "";
		//loop over entire table from start
		for(int i = 0 ; i < hashTableSize -1 ; i ++)
		{
			//if current data is not null, and equals to search add to result
			if(hashtable[i]!= null)
			{
				if(criteria.equals("First Name"))
					if(hashtable[i].getFirstName().toUpperCase().equals(name.toUpperCase()))
						result += hashtable[i] + "\n";
				
				if(criteria.equals("Last Name"))
					if(hashtable[i].getLastName().toUpperCase().equals(name.toUpperCase()))
						result += hashtable[i] + "\n";
				
				if(criteria.equals("Number"))
					if(hashtable[i].getPhone().toUpperCase().equals(name.toUpperCase()))
						result += hashtable[i] + "\n";
			}
		}

		//return result
		return result;
	}
	
	//search and return after finding first piece of data that matches search and criteria
	public String search(String name, String criteria)
	{
		String result = "";
		//loop over entire table from start
		for(int i = 0 ; i < hashTableSize -1 ; i ++)
		{
			//if current data is not null, and equals to search add to result and break out of loop
			if(hashtable[i]!= null)
			{
				if(criteria.equals("First Name"))
					if(hashtable[i].getFirstName().toUpperCase().equals(name.toUpperCase()))
					{
						result += hashtable[i] + "\n";
						break;
					}
				
				if(criteria.equals("Last Name"))
					if(hashtable[i].getLastName().toUpperCase().equals(name.toUpperCase()))
					{
						result += hashtable[i] + "\n";
						break;
					}
				
				if(criteria.equals("Number"))
				{
					if(hashtable[i].getPhone().toUpperCase().equals(name.toUpperCase()))
						result += hashtable[i] + "\n";
					break;
				}
			}
		}
		
		//return result
		return result;
	}

	//method used to access all data in the table
	public void accessAll()
	{
		int counter = 0;
		for(int i = 0 ; i < hashTableSize -1 ; i ++)
		{
			if(hashtable[i]!= null)
			{
				if(hashtable[i].getAccessed())
					hashtable[i].setAccessed(false);
				else
					hashtable[i].setAccessed(true);
				counter++;
			}
		}
		JOptionPane.showMessageDialog(null, "Accessed all entries in the HashTable, amounting to : " + counter, "Success", JOptionPane.PLAIN_MESSAGE);
	}

	//method used to find a random entry in the table
	public String findRandom()
	{
		//generate a random value and check at that point if a data value exists
		String result = "";
		Random random = new Random();
		int position = random.nextInt(hashTableSize);
		boolean found = false;

		//loop until something is found
		while(!found)
		{
			if(hashtable[position] != null)
			{
				result = hashtable[position].toString();
				found = true;
			}
			else
			{
				position = random.nextInt(hashTableSize);
			}
		}
		found = false;

		//return result
		return result;
	}
}
