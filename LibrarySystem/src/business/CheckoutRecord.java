package business;

import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord {

	private List<CheckoutRecordEntry> entries;

	private CheckoutRecord() {
		entries = new ArrayList<CheckoutRecordEntry>();
	}

	private CheckoutRecord(List<CheckoutRecordEntry> entries) {
		super();
		this.entries = entries;
	}

	public List<CheckoutRecordEntry> getEntries() {
		return entries;
	}



}
