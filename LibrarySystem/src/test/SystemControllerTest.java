package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import business.Address;
import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import controller.SystemController;
import dataaccess.DataAccessFacade;
import dataaccess.TestData;
import exception.LibrarySystemException;

public class SystemControllerTest {

	private SystemController sysCtrl;
	private static String MemberID = "984531";

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
			Book book = sysCtrl.searchBook("99-22223");
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

	// @Test
	// public void testUpdateBookInfo() {
	//
	// }

	@Test
	public void testCheckoutBook() {
		try {
			sysCtrl.checkoutBook(MemberID, "99-22223");
			LibraryMember member = sysCtrl.searchMember(MemberID);

			Book book = sysCtrl.searchBook("99-22223");
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
			sysCtrl.checkoutBook(MemberID, "99-22223");
			Book book = sysCtrl.searchBook("99-22223");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	 public void testAddBookStringStringStringStringAuthorArray() {
		 Author[] a = { new Author("Zi", "Sun", "unknown", new Address("", "", "China", ""), "died") };

		 try {
			sysCtrl.addBook("11-3311", "Art of war", "10", "3", a);
		} catch (LibrarySystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }
	//
	// @Test
	// public void testAddBookCopy() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testComputeStatus() {
	// fail("Not yet implemented");
	// }

}
