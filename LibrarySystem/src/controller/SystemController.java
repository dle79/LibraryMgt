package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.CheckoutRecordTableEntry;
import business.CopyStatus;
import business.LibraryMember;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exception.LibrarySystemException;
import exception.LoginException;

public class SystemController implements ControllerInterface {

	private static final Logger log = Logger.getLogger(SystemController.class.getName());

	public static Auth currentAuth = null;

	private static SystemController sysController = null;

	private SystemController() {

	}

	public static SystemController getInstance() {
		if (sysController == null)
			return new SystemController();
		return sysController;

	}

	/**
	 * Use case 1. Login
	 *
	 * @param id
	 * @param password
	 * @throws LoginException
	 */
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Passord does not match");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	public static Auth getCurrentAuth() {
		return currentAuth;
	}

	/**
	 * Use case 2: Add a new library member to the system.
	 *
	 * @param memberId
	 * @param firstName
	 * @param lastName
	 * @param telNumber
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @throws LibrarySystemException
	 */
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, String street,
			String city, String state, String zip) throws LibrarySystemException {
		Address addr = new Address(street, city, state, zip);
		addNewMember(memberId, firstName, lastName, telNumber, addr);
	}

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown. If new, creates a new
	 * LibraryMember based on input data and uses DataAccess to store it.
	 *
	 * @throws LibrarySystemException
	 *
	 */
	@Override
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		if (searchMember(memberId) == null) {
			DataAccess da = new DataAccessFacade();
			da.saveNewMember(new LibraryMember(memberId, firstName, lastName, telNumber, addr));
		} else {
			throw new LibrarySystemException("MemberID already exists!");
		}
	}

	/**
	 * Reads data store for a library member with specified id. Ids begin at
	 * 1001... Returns a LibraryMember if found, null otherwise
	 */
	public LibraryMember searchMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	@Override
	public void updateMemberInfo(LibraryMember member) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		if (searchMember(member.getMemberId()) == null) {
			throw new LibrarySystemException("MemberID does not exist!");
		} else {
			da.updateMember(member);
		}
	}

	@Override
	public void updateBookInfo(Book book) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		if (searchBook(book.getIsbn()) == null) {
			throw new LibrarySystemException("This book does not exist!");
		} else {
			da.updateBook(book);
		}
	}

	/**
	 * Use Case 3. Checkout a book(if available) for a library member
	 */
	@SuppressWarnings("unused")
	@Override
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
		LibraryMember member = searchMember(memberId);

		printCheckoutRecord(memberId);

		/*
		 * If ID is not found, the system will display a message indicating this
		 */
		if (member == null) {
			log.info("MemberID: " + memberId + " can not be found!");
			throw new LibrarySystemException("MemberID: " + memberId + " can not be found!");
		}

		/*
		 * If the requested book is not found or if none of the copies of the
		 * book are available, the system will return a message indicating that
		 * the item is not available
		 */
		Book book = searchBook(isbn);
		if (book == null || !book.isAvailable()) {
			log.info("Sorry, this book " + isbn + " is not available!");
			throw new LibrarySystemException("Sorry, this book " + isbn + " is not available!");
		} else {
			log.finer("book available: " + book.isAvailable());
			/*
			 * If both member ID and book ID are found and a copy is available,
			 * a new checkout record entry is created containing the copy of the
			 * requested book and the checkout date and due date
			 */
			BookCopy availableCopy = book.getNextAvailableCopy();

			LocalDate currDate = LocalDate.now();
			LocalDate returnDate = currDate.plusDays(book.getMaxCheckoutLength());
			String checkoutDate = currDate.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			String dueDate = returnDate.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));

			log.info("checkoutDate: " + checkoutDate + " dueDate :" + dueDate);
			CheckoutRecord recordOfMember = member.getRecord();
			List<CheckoutRecordEntry> entriesOfRecord = recordOfMember.getEntries();
			CheckoutRecordEntry entry = new CheckoutRecordEntry(checkoutDate, dueDate, availableCopy);

			/*
			 * This checkout entry is then added to the member’s checkout
			 * record. The copy that is checked out is marked as unavailable.
			 */

			entriesOfRecord.add(entry);

			availableCopy.changeAvailability();
			book.updateCopies(availableCopy);
			updateBookInfo(book);

			member.setRecord(recordOfMember);
			updateMemberInfo(member);

			// TO DO: on UI, the updated checkout record is displayed on the UI
			// and is also persisted.
			printCheckoutRecord(memberId);
		}

	}

	/**
	 * Optional Use Case 2. Given a library member id, print the checkout record
	 * of that library member
	 *
	 * @param memberId
	 * @return
	 */
	@Override
	public void printCheckoutRecord(String memberId) throws LibrarySystemException {
		log.info(getCheckoutRecordByMemberId(memberId).toString());
	}


	public List<CheckoutRecordTableEntry> getCheckoutTableEntryByMemberId(String memberId) {

		List<CheckoutRecordTableEntry> checkoutRecordTableEntries = new ArrayList<CheckoutRecordTableEntry>();

		CheckoutRecord crRecord;
		try {
			crRecord = getCheckoutRecordByMemberId(memberId);
			List<CheckoutRecordEntry> entries = crRecord.getEntries();
				for (CheckoutRecordEntry e : entries) {
					CheckoutRecordTableEntry mcrRecord = new CheckoutRecordTableEntry(e);
					checkoutRecordTableEntries.add(mcrRecord);
				}
		} catch (LibrarySystemException e) {
			e.printStackTrace();
		}
		return checkoutRecordTableEntries;
	}


	public CheckoutRecord getCheckoutRecordByMemberId(String memberId) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if (member == null) {
			throw new LibrarySystemException("This memberId " + memberId + "doesn't exist!");
		}
		return member.getRecord();
	}

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	/**
	 * Optional Use Case 1. Add a book to the library collection.
	 *
	 * @param isbn
	 * @param title
	 * @param maxCheckoutLength
	 * @param numOfcopies
	 * @param authors
	 * @return
	 * @throws LibrarySystemException
	 */
	public boolean addBook(String isbn, String title, String maxCheckoutLength, String numOfcopies, Author[] authors)
			throws LibrarySystemException {
		return addBook(isbn, title, Integer.parseInt(maxCheckoutLength), Integer.parseInt(numOfcopies),
				Arrays.asList(authors));
	}

	/**
	 * Looks up book by isbn to see if it exists, throw exception. Else add the
	 * book to storage
	 */
	public boolean addBook(String isbn, String title, int maxCheckoutLength, int numOfcopies, List<Author> authors)
			throws LibrarySystemException {

		if (searchBook(isbn) == null) {
			DataAccess da = new DataAccessFacade();
			@SuppressWarnings("serial")
			Book book = new Book(isbn, title, maxCheckoutLength, authors);
			book.addCopy(numOfcopies-1);
			da.saveNewBook(book);
			return true;
		} else {
			throw new LibrarySystemException("Book already exists!");
		}
	}

	/**
	 * Use Case4. Add a copy of existing book to the library collection
	 */
	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if (book == null) {
			throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
		} else {
			book.addCopy();
			DataAccess da = new DataAccessFacade();
			da.updateBook(book);
		}
		return true;
	}

	/**
	 * Optional Use Case 3. Determine whether a given copy of a book is overdue, and
	 * which member has it.
	 *
	 * @param isbn
	 * @return
	 * @throws LibrarySystemException
	 */
	List<CopyStatus> getCopyStatusListByISBN(String isbn) throws LibrarySystemException {
		BookCopy[] copies = searchBook(isbn).getCopies();
		List<CopyStatus> cpList = new ArrayList<>();
		for (BookCopy copy : copies) {
			if (!copy.isAvailable()) {
				cpList.add(computeStatus(copy));
			}
		}
		return cpList;
	}

	@Override
	public CopyStatus computeStatus(BookCopy copy) throws LibrarySystemException {
		Book book = copy.getBook();
		String isbn = book.getIsbn();
		int copyNum = copy.getCopyNum();
		if (getMemberByCopyNumAndISBN(isbn, copyNum) == null)
			throw new LibrarySystemException("No member had checked this book!");
		LibraryMember member = getMemberByCopyNumAndISBN(isbn, copyNum);

		CopyStatus copyStatus = new CopyStatus(isbn);
		copyStatus.setTitle(book.getTitle());
		copyStatus.setCopyNum(String.valueOf(copy.getCopyNum()));
		copyStatus.setMemberName(member.getFirstName() + " " + member.getLastName());
		copyStatus.setDueBack(getDueDateByMemberAndISBN(member, isbn));
		if (copy.isAvailable()) {
			copyStatus.setAvailability(CopyStatus.AVAILABLE);
		} else {
			copyStatus.setAvailability(CopyStatus.NOT_AVAILABLE);
		}

		return copyStatus;
	}

	private LibraryMember getMemberByCopyNumAndISBN(String isbn, int copyNum) {
		DataAccessFacade daf = new DataAccessFacade();
		return daf.searchMemberByBook(isbn, copyNum);
	}

	public String getDueDateByMemberAndISBN(LibraryMember member, String isbn) {
		List<CheckoutRecordEntry> entries = member.getRecord().getEntries();
		for (CheckoutRecordEntry entry : entries) {
			if (entry.getCopy().getBook().getIsbn().equals(isbn)) {
				String dueDate = entry.getDueDate();
				int temp = LocalDate.now().compareTo(
						LocalDate.parse(dueDate, DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN)));
				return temp > 0 ? CopyStatus.OVER_DUE : dueDate;
			}
		}
		return "Unknown";
	}

	public Auth searchAuth(String username) throws LoginException{
		DataAccessFacade daf = new DataAccessFacade();
		if(username.equalsIgnoreCase("") || username == null){
			throw new LoginException("Login ID should not be empty!");
		}
		return daf.searchAuth(username);

	}

	public static void main(String[] args) throws LibrarySystemException {
		try {
			// new SystemController().addBookCopy("28-12331");
			// new SystemController().checkoutBook("984521", "28-12331");
			// new SystemController().addNewMember("984531", "Brad", "Peter",
			// "737-999-1234", "S. 1st Avenue", "San Francisco", "CA", "93136");
			// System.out.println(new
			// SystemController().searchMember("984531"));
			// Author[] a = { new Author("Zi", "Sun", "unknown", new Address("",
			// "", "China", ""), "died") };
			//
			// new SystemController().addBook("11-3311", "Art of war", "10",
			// "3", a);
			// System.out.println(new SystemController().searchBook("11-3311"));
			 new SystemController().checkoutBook("984531", "99-22223");
			// new SystemController().checkoutBook("984520", "99-22223");
			System.out.println(new SystemController().getCopyStatusListByISBN("99-22223"));
		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
		}
	}

	public void setUserAuth(Auth auth) {
		currentAuth = auth;
	}
	public Auth getUserAuth() {
		return currentAuth;
	}
}
