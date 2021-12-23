package Test;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.*;
import components.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Main {
	
	public static void main(String[] args) {
		
		Main myCollection = new Main();

		ArrayList<Client> clients = myCollection.generateTestSet(10);
		//myCollection.displayClients(clients);
		
		ArrayList<Account> accounts = myCollection.loadAccount(clients);
		//myCollection.displayAccounts(accounts);
					
		Hashtable<Integer, Account> accountHashTable = myCollection.creatHashtable(accounts);
		myCollection.displayHashTable(accountHashTable);
		
		// Fill the flowArray.		
		for (Account account: myCollection.accountHashTable.values()) {
			if (account instanceof CurrentAccount){
				Credit flow = new Credit("comment","Credit",100.50d,
						account.getAccountNumber(),true,LocalDate.now().plusDays(2));
				myCollection.loadFlowArray(flow);
			}
		}
		
		for (Account account: myCollection.accountHashTable.values()) {
			if (account instanceof SavingsAccount){
				Credit flow = new Credit("comment","Credit",1500.00d,
						account.getAccountNumber(),true,LocalDate.now().plusDays(2));
				myCollection.loadFlowArray(flow);
			}
		}
		
		Debit flow1 = new Debit("comment","Debit",50.00d,1,true,LocalDate.now().plusDays(2));
		Transfer flow3 = new Transfer("comment","Transfer",50.00d,2,1,true,LocalDate.now().plusDays(2));
		myCollection.loadFlowArray(flow1); myCollection.loadFlowArray(flow3);
		
		//Debit flow2 = new Debit("comment","Debit",500.00d,3,true,LocalDate.now().plusDays(2));
		//Transfer flow4 = new Transfer("comment","Transfer",1550.00d,4,17,true,LocalDate.now().plusDays(2));
		//myCollection.loadFlowArray(flow4); myCollection.loadFlowArray(flow2);
		

		// Display the flowArray.
		myCollection.displayFlows();
		
		// Update account and display the updated accountHashTable.
		Account.updateBalance(myCollection.flowArray, accountHashTable);
		myCollection.displayHashTable(accountHashTable);
		
		// Create JSON and XML files.
		Main.loadJSON(myCollection.flowArray);
		Main.loadXML(accountHashTable);

	}
	
//1.1.2 Creation of main class for tests	
	ArrayList<Client> collectionOfClients = new ArrayList<Client>(); 
	public ArrayList<Client> generateTestSet(int number) {
		for (int i = 1; i < number; i++) {
			String clientName = "name" + i;
			String clientFirstName = "firstname" + i;
			Client newClient = new Client(clientName,clientFirstName);
			collectionOfClients.add(newClient);
			}
		return collectionOfClients;
	}
	
	public void displayClients(ArrayList<Client> array) {  
		array.stream()
			.map( Object::toString )
			.forEach(System.out::println);
		 }
	
//1.2.3 Creation of the table account
	static int i = 0;
	ArrayList<Account> collectionOfAccounts = new ArrayList<Account>(); 
	public ArrayList<Account> loadAccount(ArrayList<Client> collectionOfClients){
		
		for (Client client: collectionOfClients) {
			i++;
			CurrentAccount newCurrentAccount = new CurrentAccount("Current"+i, client);
			SavingsAccount newSavingsAccount = new SavingsAccount("Savings"+i, client);

			collectionOfAccounts.add(newCurrentAccount);
			collectionOfAccounts.add(newSavingsAccount);		
		}
		return collectionOfAccounts;
	}
	
	public void displayAccounts(ArrayList<Account> array) {  
		array.stream()
			.map( Object::toString )
			.forEach(System.out::println);
		 }
	
// 1.3.1 Adaptation of the table of accounts
	Hashtable<Integer, Account> accountHashTable = new Hashtable<>(collectionOfAccounts.size());
	public Hashtable<Integer, Account> creatHashtable(ArrayList<Account> collectionOfAccounts) {        
        for (Account account: collectionOfAccounts) {
        	accountHashTable.put(account.getAccountNumber(), account);
        }
    	return accountHashTable;
    }	

	public void displayHashTable(Hashtable<Integer, Account> hashtable) { 
		List<Account> accountList = new ArrayList<Account>( hashtable.values() );
			
		 accountList.stream()
				  .sorted(Comparator.comparing(Account::getBalance))
				  .map( Object::toString )
				  .forEach(System.out::println);			
		 }
	
//1.3.4 Creation of the flow array
	ArrayList<Flow> flowArray = new ArrayList<Flow>();
	public void loadFlowArray(Flow newFlow) {
		flowArray.add(newFlow);
		}
	
	public void displayFlows() { 
		for (Flow flow: flowArray) {
			System.out.println(flow.getIdentifier()+" "+flow.getAmount()+
					"\t target account number : "+flow.getTargetAccountNumber()+" issuing account number : "+
					flow.getIssuingAccountNumber()+" "+flow.getEffect()+ " "+flow.getDateOfFlow()+" "+ flow.getComment() );   		     
		 }
    }
	
//2.1 JSON file of flows.

	public static void loadJSON(ArrayList<Flow> flowArray) {
		
        Path path = Paths.get("C:\\JSONfiles\\fileofflows.json");
		JSONArray jsArray = new JSONArray(flowArray);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Flows",jsArray);
				
	    try (FileWriter file = new FileWriter(path.toString())) {
	        file.write(jsonObject.toString());
      }
	    catch (IOException e) {
	         e.printStackTrace();
	    }
   }
	
//2.2 XML file of account.

	public static void loadXML(Hashtable<Integer, Account> hashtable) {
		
        Path path = Paths.get("C:\\XMLfiles\\fileofaccounts.xml");
       // File file = new File(path.toString());        
        Set<Integer> setOfKeys = hashtable.keySet();
        	
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement = doc.createElementNS("Array","Accounts");
            //append root element to document
            doc.appendChild(rootElement);

            //append first child element to root element
            for (Integer key : setOfKeys) {          
            	Account account = hashtable.get(key);
            	rootElement.appendChild(getEmployee(doc, account));
            }
            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to file
            StreamResult file = new StreamResult(new File(path.toString()));

            //write data
            transformer.transform(source, file);
            System.out.println("XML CREATED");
        }
         catch (Exception e) {
            e.printStackTrace();
         }
        
	}

    public static Node getEmployee(Document doc, Account newaccount) {
        Element account = doc.createElement("Account");

        //set id attribute
        account.setAttribute("AccountNumber",  Integer.toString(newaccount.getAccountNumber()));

        //create name element
        account.appendChild(getEmployeeElements(doc, account, "Label", newaccount.getLabel()));

        //create age element
        account.appendChild(getEmployeeElements(doc, account, "Balance", String.valueOf(newaccount.getBalance())));

        //create role element
        account.appendChild(getEmployeeElements(doc, account, "Client", String.valueOf(newaccount.getClient())));

        return account;
    }

    //utility method to create text node
    public static Node getEmployeeElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}
	


	


	
	
	
	
	
	
	
	
	