package business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberCheckoutRecord {

	private final SimpleStringProperty isbn = new SimpleStringProperty("");
	private final SimpleStringProperty title = new SimpleStringProperty("");
	private final SimpleStringProperty copyNum = new SimpleStringProperty("");
	private final SimpleStringProperty checkoutDate = new SimpleStringProperty("");
	private final SimpleStringProperty dueDate = new SimpleStringProperty("");

	public MemberCheckoutRecord(String isbn, String t, String copyNum, String outDate, String dueDate) {
		setISBN(isbn);
		setTitle(t);
		setCopyNum(copyNum);
		setCheckoutDate(outDate);
		setDueDate(dueDate);
	}

	public void setISBN(String i) {
		isbn.set(i);
	}

	public String getISBN() {
		return this.isbn.get();
	}

	public void setTitle(String t) {
		this.title.set(t);
	}

	public String getTitle() {
		return this.title.get();
	}

	public void setCopyNum(String copyNum) {
		this.copyNum.set(copyNum);
	}

	public String getCopyNum() {
		return this.copyNum.get();
	}

	public void setCheckoutDate(String outDate) {
		this.checkoutDate.set(outDate.toString());
	}

	public String getCheckoutDate() {
		return this.checkoutDate.get();
	}

	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate.toString());
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
	

}
