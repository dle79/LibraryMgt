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

	public List<CheckoutRecordEntry> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		return "CheckoutRecord [entries=" + entries + "]";
	}



}
