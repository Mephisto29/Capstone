//this class represents an address book entry
public class AdressBookEntry 
{
	//private data members used to create an entry
	private String firstName;
	private String lastName;
	private String company;
	private String address;
	private String city;
	private String county;
	private String state;
	private String zip;
	private String phone;
	private String fax;
	private String email;
	private String web;
	private boolean accessed;
	
	//getters and setters
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getCompany() 
	{
		return company;
	}
	
	public void setCompany(String company) 
	{
		this.company = company;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	public String getCity() 
	{
		return city;
	}
	
	public void setCity(String city) 
	{
		this.city = city;
	}
	
	public String getCounty() 
	{
		return county;
	}
	
	public void setCounty(String county) 
	{
		this.county = county;
	}
	
	public String getState() 
	{
		return state;
	}
	
	public void setState(String state) 
	{
		this.state = state;
	}
	
	public String getZip() 
	{
		return zip;
	}
	
	public void setZip(String zip) 
	{
		this.zip = zip;
	}
	
	public String getPhone() 
	{
		return phone;
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}
	
	public String getFax() 
	{
		return fax;
	}
	
	public void setFax(String fax) 
	{
		this.fax = fax;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getWeb()
	{
		return web;
	}
	
	public void setWeb(String web) 
	{
		this.web = web;
	}
	
	public void setAccessed(boolean access)
	{
		accessed = access;
	}
	
	public boolean getAccessed()
	{
		return accessed;
	}
	//end of getters abd setters
	
	//constructor
	public AdressBookEntry(	String firstName,
							String lastName,
							String company,
							String address,
							String city,
							String county,
							String state,
							String zip,
							String phone,
							String fax,
							String email,
							String web)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
		this.address = address;
		this.city = city;
		this.county = county;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.web = web;
		this.accessed = false;
	}
	
	//to string used to display the data
	public String toString()
	{
		return firstName + ":" + lastName + ":" + company + ":" + address + ":" + city + ":" + county + ":" + state + ":" + zip + ":" + phone + ":" + fax + ":" + email + ":" + web;
	}
}
