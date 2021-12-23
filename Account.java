
package components;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Optional;  

//1.2.1 Creation of the account class
public abstract class Account {
	
	protected static int count = 0;
	protected String label;
	protected double balance;
	protected int accountNumber;
	protected Client client;
	
	public Account(String accountLabel, Client accountClient) {
		label = accountLabel;
		client = accountClient;
		accountNumber = ++count;
	}

//1.3.5 Updating accounts

	public static void updateBalance(ArrayList<Flow> flowsArray,Hashtable<Integer, Account> accountHashTable ) {
		
		Predicate<Account> negativeBalance =  x -> x.getBalance() < 0.00;
		Set<Entry<Integer, Account> > entrySet = accountHashTable.entrySet();
		for (Flow flow: flowsArray) {
			for (Entry<Integer, Account> entry : entrySet) {
				
				if (entry.getKey() == flow.getTargetAccountNumber() || entry.getKey() == flow.getIssuingAccountNumber()){
					Account account = entry.getValue();
					account.setBalance(flow);
					
				Optional<Account> op = Optional.of(account);
				op.filter(negativeBalance)
					.ifPresent(o->System.out.println("Negative balance  "+ o.toString()));
				}
			}
		}
    }
	
	public double getBalance() {  
		  return balance;  
		}

	public void setBalance(Flow newFlow) {
		switch(newFlow.getIdentifier().toLowerCase()) {
			case "credit": this.balance += newFlow.getAmount();
				break;
				
			case "debit": this.balance -= newFlow.getAmount();
				break;

			case "transfer":
				if(this.accountNumber==newFlow.getTargetAccountNumber()) {
					this.balance += newFlow.getAmount();
				}
				else{ // this.accountNumber==newFlow.getIssuingAccountNumber()
					this.balance -= newFlow.getAmount();
				}
				break;
				
				default:System.out.println("Error of flow identifier.");			
		}
	}
	
	public int getAccountNumber() {  
		  return accountNumber;  
		}
	
	public void setAccountNumber(int number) {
		this.accountNumber = number;
	}
	
	public String getLabel() {  
		  return label;  
		}
	
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
	
	public Client getClient() {  
		  return client;  
		}
	
	public void setClient(Client newClient) {
		this.client = newClient;
	}
	
	public String toString() { 
		  return "Account number : "+accountNumber+" "+label+" Balance : "+ balance+" "+ " "+ client ;  
		 } 
}
