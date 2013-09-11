//this is the main driver class that loads the GUI and starts the GUI thread
public class Main 
{
	public static void main(String[] args)
	{
		//create reader object
		Reader read = new Reader();
		//create GUI and pass reader object and start the GUI thread
		GUI gui = new GUI(read);
		gui.start();
	}
}
