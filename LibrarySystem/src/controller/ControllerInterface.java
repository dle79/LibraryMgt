package controller;

import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import exception.LibrarySystemException;

public interface ControllerInterface {
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException;

	public LibraryMember searchMember(String memberId);

	public void updateMemberInfo(LibraryMember member) throws LibrarySystemException;

	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException;

	public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors)
			throws LibrarySystemException;

	public void updateBookInfo(Book book) throws LibrarySystemException;

	public boolean addBookCopy(String isbn) throws LibrarySystemException;

	public void printCheckoutRecord(String memberId) throws LibrarySystemException;

	// public CopyStatus computeStatus(BookCopy copy);
	public Book searchBook(String isbn);

}
