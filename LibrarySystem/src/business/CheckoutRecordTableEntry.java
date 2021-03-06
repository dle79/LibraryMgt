package business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CheckoutRecordTableEntry {

	private final SimpleStringProperty isbn = new SimpleStringProperty("");
	private final SimpleStringProperty title = new SimpleStringProperty("");
	private final SimpleStringProperty copyNum = new SimpleStringProperty("");
	private final SimpleStringProperty checkoutDate = new SimpleStringProperty("");
	private final SimpleStringProperty dueDate = new SimpleStringProperty("");
	private final SimpleStringProperty memberID = new SimpleStringProperty("");
	
	// Constructor for Due Date
	public CheckoutRecordTableEntry(String isbn, String title, String copyNum, String memberID, String dueDate) {
		this.isbn.set(isbn);
		this.memberID.set(memberID);
		this.title.set(title);
		this.dueDate.set(dueDate);
		this.copyNum.set(copyNum);
	}
	
	// Constructor for both CheckoutRecord and Search
	public CheckoutRecordTableEntry(CheckoutRecordEntry entry) {
		isbn.set(entry.getCopy().getBook().getIsbn());
		title.set(entry.getCopy().getBook().getTitle());
		copyNum.set(String.valueOf(entry.getCopy().getCopyNum()));
		checkoutDate.set(entry.getCheckoutDate());
		dueDate.set(entry.getDueDate());
	}

	public String getISBN() {
		return this.isbn.get();
	}

	public String getTitle() {
		return this.title.get();
	}

	public String getCopyNum() {
		return this.copyNum.get();
	}

	public String getCheckoutDate() {
		return this.checkoutDate.get();
	}

	public String getDueDate() {
		return this.dueDate.get();
	}

	public StringProperty isbnProperty() {
		return this.isbn;
	}

	public StringProperty titlePropery() {
		return this.title;
	}

	public StringProperty copyNumPropery() {
		return this.copyNum;
	}

	public StringProperty checkoutDatePropery() {
		return this.checkoutDate;
	}

	public StringProperty dueDatePropery() {
		return this.dueDate;
	}

	public StringProperty memberIDPropery() {
		return this.memberID;
	}
}
