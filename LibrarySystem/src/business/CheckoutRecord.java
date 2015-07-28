package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {


	private static final long serialVersionUID = 7157555046172080729L;
	private List<CheckoutRecordEntry> entries;

	CheckoutRecord() {
		entries = new ArrayList<CheckoutRecordEntry>();
	}

//	private CheckoutRecord(List<CheckoutRecordEntry> entries) {
//		this.entries = entries;
//	}

	public List<CheckoutRecordEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<CheckoutRecordEntry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "CheckoutRecord [entries=" + entries + "]";
	}



}
