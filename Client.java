package components;
//1.1.1 Creation of the client class
public class Client {
	
	private String name;
	private String firstName;
	private int clientNumber;
	static private int count = 0;

	public Client(String clientName, String clientFirstName) {
		 name = clientName;
		 firstName = clientFirstName;
		 clientNumber = ++count;
	}
	
	public String getName() {  
	  return name;  
	}  
	
	public String getFirstName() {  
	  return firstName;  
	}  
	
	public int getClientNumber() {  
		  return clientNumber;  
		} 
	
	public void setName(String clientName) {  
	    this.name = clientName;  
	}  
	
	public void setFirstName(String clientFirstName) {  
	    this.firstName = clientFirstName;  
	}  
	
	public void setclientNumber(int number) {  
	    this.clientNumber = number;  
	} 
	
	public String toString() { 
		  return "Client number : " +clientNumber+" "+name+" " + firstName;  
		 }  
}