package components;

import java.time.LocalDate;
//1.3.3 Creation of the Credit class.
public class Credit extends Flow {

	public Credit(String newComment, String newIdentifier, double newAmount, int newTargetAccountNumber,
			boolean newEffect, LocalDate newDateOfFlow) {
		super(newComment, newIdentifier, newAmount, newTargetAccountNumber, newEffect, newDateOfFlow);
	}

}
