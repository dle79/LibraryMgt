package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.CopyStatus;
import business.LibraryMember;
import controller.SystemController;
import dataaccess.DataAccessFacade;
import dataaccess.TestData;
import exception.LibrarySystemException;

public class SystemControllerTest {

	private SystemController sysCtrl;
	private static String MemberID = "984531";
	private static String ISBN = "99-22223";

	@Before
	public void setUp() throws Exception {
		sysCtrl = SystemController.getInstance();
		TestData.prepareData();
		sysCtrl.addNewMember(MemberID, "Brad", "Peter", "737-999-1234", "S. 1st Avenue", "San Francisco", "CA",
				"93136");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNewMember() {

		LibraryMember expectedMember = new LibraryMember("984531", "Brad", "Peter", "737-999-1234",
				new Address("S. 1st Avenue", "San Francisco", "CA", "93136"));
		LibraryMember actualMember = sysCtrl.searchMember("984531");
		Assert.assertTrue(expectedMember.toString().equals(actualMember.toString()));

	}

	@Test
	public void testUpdateMemberInfo() {
		try {

			LibraryMember peter = sysCtrl.searchMember(MemberID);
			CheckoutRecord record = new CheckoutRecord();
			LocalDate today = LocalDate.now();
			Book book = sysCtrl.searchBook(ISBN);
			CheckoutRecordEntry entry = new CheckoutRecordEntry(today.toString(),
					today.plusDays(book.getMaxCheckoutLength()).toString(), book.getNextAvailableCopy());
			record.getEntries().add(entry);
			peter.setRecord(record);
			sysCtrl.updateMemberInfo(peter);
			LibraryMember member = sysCtrl.searchMember(MemberID);

			Assert.assertTrue(member.getRecord().toString().equals(record.toString()));

		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	@Test
	public void testCheckoutBook() {
		try {
			sysCtrl.checkoutBook(MemberID, ISBN);
			LibraryMember member = sysCtrl.searchMember(MemberID);

			Book book = sysCtrl.searchBook(ISBN);
			CheckoutRecordEntry actualEntry = member.getRecord().getEntries().get(0);
			LocalDate today = LocalDate.now();
			String checkoutDate = today.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			String dueDate = today.plusDays(book.getMaxCheckoutLength())
					.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			CheckoutRecordEntry expecedEntry = new CheckoutRecordEntry(checkoutDate, dueDate,
					member.getRecord().getEntries().get(0).getCopy());

			Assert.assertTrue(expecedEntry.toString().equals(actualEntry.toString()));

		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCheckoutRecordByMemberId() {
		try {
			sysCtrl.checkoutBook(MemberID, ISBN);
			Book book = sysCtrl.searchBook(ISBN);
			LibraryMember member = sysCtrl.searchMember(MemberID);
			CheckoutRecord actualRecord = sysCtrl.getCheckoutRecordByMemberId(MemberID);
			CheckoutRecord expectedRecord = new CheckoutRecord();
			LocalDate today = LocalDate.now();
			String checkoutDate = today.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			String dueDate = today.plusDays(book.getMaxCheckoutLength())
					.format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			expectedRecord.getEntries().add(
					new CheckoutRecordEntry(checkoutDate, dueDate, member.getRecord().getEntries().get(0).getCopy()));

			Assert.assertTrue(expectedRecord.toString().equals(actualRecord.toString()));
		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Test
	public void testAddBookStringStringStringStringAuthorArray() {
		List<Author> authors = new ArrayList<>();
		authors.add(new Author("Zi", "Sun", "none", new Address("none", "none", "Middle Kingdom", "none"), "Died"));

		try {
			sysCtrl.addBook("11-3311", "Art of war", 10, 3, authors);
			Book actualBook = sysCtrl.searchBook("11-3311");
			Book expectedbook = new Book("11-3311", "Art of war", 10, authors);
			expectedbook.addCopy(2);

			Assert.assertTrue(expectedbook.toString().equals(actualBook.toString()));

		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Test
	public void testAddBookCopy() {

		Book book = sysCtrl.searchBook(ISBN);
		int actualCopies = book.getNumCopies();
		int expectedCopy1 = actualCopies + 1;
		book.addCopy();
		int actualCopies1 = book.getNumCopies();

		int expectedCopy2 = actualCopies + 3;
		book.addCopy(2);
		int actualCopies2 = book.getNumCopies();

		int[] expectedCopyNum = { expectedCopy1, expectedCopy2 };
		int[] actualsCopyNum = { actualCopies1, actualCopies2 };
		Assert.assertArrayEquals(expectedCopyNum, actualsCopyNum);

	}

	@Test
	public void testComputeStatus() {
		try {
			sysCtrl.checkoutBook(MemberID, ISBN);
			Book book = sysCtrl.searchBook(ISBN);
			BookCopy copy = book.getCopy(1);

			CopyStatus actualstatus = sysCtrl.computeStatus(copy);
			CopyStatus expectedstatus = new CopyStatus(ISBN);
			expectedstatus.setMemberName("Brad Peter");
			expectedstatus.setTitle("Thinking Java");
			expectedstatus.setCopyNum("1");
			expectedstatus.setDueBack(sysCtrl.getDueDateByMemberAndISBN(sysCtrl.searchMember(MemberID), ISBN));
			expectedstatus.setAvailability(CopyStatus.NOT_AVAILABLE);

			Assert.assertTrue(expectedstatus.toString().equals(actualstatus.toString()));

		} catch (LibrarySystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
