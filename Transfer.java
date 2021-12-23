package components;

import java.time.LocalDate;
//1.3.3 Creation of the Transfert class.
public class Transfer extends Flow {
		
	public Transfer(String newComment, String newIdentifier, double newAmount, int newTargetAccountNumber,
			int issuingAccountNumber, boolean newEffect, LocalDate newDateOfFlow) {
		super(newComment, newIdentifier, newAmount, newTargetAccountNumber, newEffect, newDateOfFlow);
		this.issuingAccountNumber = issuingAccountNumber;
	}

}
