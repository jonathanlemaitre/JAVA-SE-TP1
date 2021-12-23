package components;
import java.time.LocalDate;
//1.3.2 Creation of the Flow class
public abstract class Flow {
	
	private String comment;
	private String identifier;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private LocalDate dateOfFlow;
	public int issuingAccountNumber;
	
	public Flow(String newComment, String newIdentifier,double newAmount,int newTargetAccountNumber,
			boolean newEffect,LocalDate newDateOfFlow) {

		 	 comment = newComment;
			 identifier = newIdentifier;
			 amount = newAmount;
			 targetAccountNumber = newTargetAccountNumber;
			 effect = newEffect;
			 dateOfFlow = newDateOfFlow;
	}

	public void setComment(String newComment) {
		this.comment = newComment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setIdentifier(String newIdentifier) {
		this.identifier = newIdentifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setAmount(double newAmount) {
		this.amount = newAmount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setTargetAccountNumber(int newTargetAccountNumber) {
		this.targetAccountNumber = newTargetAccountNumber;
	}
	
	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}
	
	public void setEffect(boolean newEffect) {
		this.effect = newEffect;
	}
	
	public boolean getEffect() {
		return effect;
	}
	
	public void setDateOfFlow(LocalDate newDateOfFlow) {
		this.dateOfFlow = newDateOfFlow;
	}
	
	public LocalDate getDateOfFlow() {
		return dateOfFlow;
	}
	
	public int getIssuingAccountNumber() {
		return issuingAccountNumber;
	}
	 
}


