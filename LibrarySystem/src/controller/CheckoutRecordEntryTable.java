package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CheckoutRecordEntryTable {
	
    private SimpleIntegerProperty bookCopyNum;
    private SimpleStringProperty checkoutDate;
    private SimpleStringProperty dueDate;

    public CheckoutRecordEntryTable(int bookCopyNum, String checkoutDate, String dueDate) {

        this.bookCopyNum = new SimpleIntegerProperty(bookCopyNum);
        this.checkoutDate = new SimpleStringProperty(checkoutDate);
        this.dueDate = new SimpleStringProperty(dueDate);

    }

    public int getBookCopyNum() {
        return bookCopyNum.get();
    }

    public String getCheckoutDate() {
        return checkoutDate.get();
    }

    public String getDueDate() {
        return dueDate.get();
    }

}
