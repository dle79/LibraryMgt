package business;

public class CheckoutRecordEntry {
	private String checkoutDate;
	private String dueDate;
	private BookCopy copy;

	private CheckoutRecordEntry(String checkoutDate, String dueDate, BookCopy copy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.setCopy(copy);
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

	public BookCopy getCopy() {
		return copy;
	}

	public void setCopy(BookCopy copy) {
		this.copy = copy;
	}

}
