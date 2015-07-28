package controller;

import java.util.HashMap;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exception.LibrarySystemException;
import exception.LoginException;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	private static SystemController sysController = null;

	private SystemController() {

	}

	public static SystemController getInstance(){
		if(sysController == null)
			return new SystemController();
		return sysController;

	}

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Passord does not match");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown.
	 * If new, creates a new LibraryMember based on
	 * input data and uses DataAccess to store it.
	 *
	 */
	public void addNewMember(String memberId, String firstName, String lastName,
			String telNumber, Address addr) throws LibrarySystemException {
		if(searchMember(memberId) == null){
			DataAccess da = new DataAccessFacade();
			da.saveNewMember(new LibraryMember(memberId, firstName, lastName, telNumber, addr));
		}else{
			throw new LibrarySystemException("MemberID already exists!");
		}
	}

	/**
	 * Reads data store for a library member with specified id.
	 * Ids begin at 1001...
	 * Returns a LibraryMember if found, null otherwise
	 *
	 */
	public LibraryMember searchMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	@Override
	public void updateMemberInfo(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		addNewMember(memberId, firstName, lastName, telNumber, addr);
	}

	/**
	 * Looks up Book by isbn from data store. If not found, an exception is thrown.
	 * If no copies are available for checkout, an exception is thrown.
	 * If found and a copy is available, member's checkout record is
	 * updated and copy of this publication is set to "not available"
	 */
	@Override
	public void checkoutBook(String memberId, String isbn)
			throws LibrarySystemException {
		// TODO Auto-generated method stub
		// 1. If ID is not found, the system will display a message indicating this
		// 2. if the requested book is not found or if none of the  copies of the book
		//    are available, the system will return a message indicating that the item is not available
		// 3.  If both member ID and book ID are found and a copy is available, a new checkout record entry is created
		//    containing the copy of the requested book and the checkout date and due date
		// 4. This checkout entry is then added to the member’s checkout record.
		// 		The copy that is checked out is marked as unavailable. The updated checkout
		//		record is displayed on the UI and is also persisted.
	}

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	/**
	 * Looks up book by isbn to see if it exists, throw exception.
	 * Else add the book to storage
	 */
	public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors)
			throws LibrarySystemException {

		if(searchBook(isbn) == null){
			DataAccess da = new DataAccessFacade();
			da.saveNewBook(new Book(isbn, title, maxCheckoutLength, authors));
			return true;
		}else{
			throw new LibrarySystemException("Book already exists!");
		}
	}


	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if(book == null) throw new LibrarySystemException("No book with isbn " + isbn
			+ " is in the library collection!");
		book.addCopy();
		return true;
	}


	public static void main(String[] args) throws LibrarySystemException {
		try {
			new SystemController().login("101", "xyzz");
		} catch (LoginException e) {
			System.out.println(e.getMessage());
		}
	}











}
