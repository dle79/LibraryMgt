package business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookOverdueRecord {

	private final SimpleStringProperty isbn = new SimpleStringProperty("");
	private final SimpleStringProperty title = new SimpleStringProperty("");
	private final SimpleStringProperty memberID = new SimpleStringProperty("");
	private final SimpleStringProperty checkoutDate = new SimpleStringProperty("");
	private final SimpleStringProperty dueDate = new SimpleStringProperty("");	

	public BookOverdueRecord() {

	}

	public BookOverdueRecord(String isbn, String title, String memberID, String outDate, String dueDate) {
		setIsbn(isbn);
		setMemberID(memberID);
		setTitle(title);
		setCheckoutDate(outDate);
		setDueDate(dueDate);
	}

	public void setIsbn(String i) {
		isbn.set(i);
	}

	public String getIsbn() {
		return this.isbn.get();
	}

	public void setMemberID(String memberID) {
		this.memberID.set(memberID);
	}

	public String getMemberID() {
		return this.memberID.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getTitle() {
		return this.title.get();
	}

	public void setCheckoutDate(String outDate) {
		this.checkoutDate.set(outDate);
	}

	public String getCheckoutDate() {
		return this.checkoutDate.get();
	}

	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}

	public String getDueDate() {
		return this.dueDate.get();
	}

	public StringProperty isbnProperty() {
		return this.isbn;
	}

	public StringProperty memberPropery() {
		return this.memberID;
	}

	public StringProperty titlePropery() {
		return this.title;
	}

	public StringProperty checkoutDatePropery() {
		return this.checkoutDate;
	}

	public StringProperty dueDatePropery() {
		return this.dueDate;
	}
		
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.isbn + "\t");
		sb.append(this.memberID + "\t");
		sb.append(this.title + "\t");		
		sb.append(this.checkoutDate + "\t");
		sb.append(this.dueDate + "\t");

		return sb.toString();
	}

}
