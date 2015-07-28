package business;

public class CheckoutRecordEntry {
	private String checkoutDate;
	private String dueDate;
	private BookCopy copy;

	public CheckoutRecordEntry(String checkoutDate, String dueDate, BookCopy copy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.copy = copy;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	@Override
	public String toString() {
		return "CheckoutRecordEntry [checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", copyNum=" + copy.getCopyNum() + "]";
	}


}
