package business;

import java.io.Serializable;

public class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = -8869021564644625554L;
	private String checkoutDate;
	private String dueDate;
	private BookCopy copy;

	public BookCopy getCopy() {
		return copy;
	}

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
		return "CheckoutRecordEntry [checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", BookName=" + copy.getBook().getTitle() + ", copyNum="
				+ copy.getCopyNum() + "]";
	}

}
