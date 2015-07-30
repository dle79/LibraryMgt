package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.AddCheckoutRecord;
import ui.AddNewBook;
import ui.AddNewBookCopy;
import ui.AddNewMember;
import ui.EditLibraryMember;
import ui.PopupMessage;
import ui.PrintCheckoutRecord;
import ui.SearchBookOverdue;
import business.Address;
import business.Author;
import business.Book;
import business.BookOverdueRecord;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import business.CheckoutRecordTableEntry;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import exception.LibrarySystemException;
import exception.LoginException;

public class WindowsController implements Initializable {

	private static final Logger log = Logger.getLogger(WindowsController.class
			.getName());

	private SystemController sysController = null;

	@FXML
	private TextField usernameTfd;

	@FXML
	private PasswordField passwordTfd;

	@FXML
	private Label promptLabel;

	@FXML
	private Button loginBtn;

	@FXML
	private Button resetBtn;

	@FXML
	private MenuBar menuBar;

	// Attributes for add LibraryMember (look at AddNewMember.fxml)
	@FXML
	private Button closeNewMemberBtn;

	@FXML
	private Button saveNewMemberBtn;

	@FXML
	private TextField memberIdTfd;

	@FXML
	private TextField memberFirstNameTfd;

	@FXML
	private TextField memberLastNameTfd;

	@FXML
	private TextField memberPhoneTfd;

	@FXML
	private TextField memberStreetTfd;

	@FXML
	private TextField memberCityTfd;

	@FXML
	private TextField memberStateTfd;

	@FXML
	private TextField memberZipTfd;

	// Attributes for edit LibraryMember (look at EditLibraryMember.fxml)
	@FXML
	private Button editLibraryMemberSearchBtn;

	@FXML
	private Button closeEditMemberBtn;

	@FXML
	private Button saveEditMemberBtn;

	@FXML
	private TextField editMemberIdTfd;

	@FXML
	private TextField editMemberFirstNameTfd;

	@FXML
	private TextField editMemberLastNameTfd;

	@FXML
	private TextField editMemberPhoneTfd;

	@FXML
	private TextField editMemberStreetTfd;

	@FXML
	private TextField editMemberCityTfd;

	@FXML
	private TextField editMemberStateTfd;

	@FXML
	private TextField editMemberZipTfd;

	// Attributes for add new book copy (look at AddNewBookCopy.fxml)
	@FXML
	private Button saveNewBookCopyBtn;
	@FXML
	private Button closeNewBookCopyBtn;
	@FXML
	private TextField isbn;
	// Attributes for add new book (look at AddNewBook.fxml)
	@FXML
	private Button closeNewBookBtn;
	@FXML
	ListView<Author> authorListView;
	@FXML
	private TextField title;
	@FXML
	private TextField maxcheckoutlength;
	@FXML
	private TextField numofcopies;
	@FXML
	private Button closePrintCheckoutBtn;
	// Attributes for add new book (look at PrintCheckoutRecord.fxml)
	@FXML
	private TextField memberID;

	@FXML
	TableView<CheckoutRecordTableEntry> checkoutsView;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> isbnCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> titleCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> copyNumCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> checkoutDateCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> dueDateCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> memberIDCol;
	// Attributes for add new book (look at BookOverDue.fxml)
	@FXML
	TableView<BookOverdueRecord> bookoverduesView;
	@FXML
	private TableColumn<BookOverdueRecord, String> isbnBOCol;
	@FXML
	private TableColumn<BookOverdueRecord, String> titleBOCol;
	@FXML
	private TableColumn<BookOverdueRecord, String> memberIDBOCol;
	//@FXML
	//private TableColumn<BookOverdueRecord, String> checkoutDateBOCol;
	@FXML
	private TableColumn<BookOverdueRecord, String> dueDateBOCol;


	@FXML
	private Button closeCheckBookOverDueBtn;

	private void commonCloseButtonHandler(Button closeButton) {
		// get a handle to the stage
		Stage stage = (Stage) closeButton.getScene().getWindow();
		// do what you have to do
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String path = arg0.getPath();
		String[] s = path.split("/");

		if (s[s.length - 1].equals("PrintCheckoutRecord.fxml")) {
			isbnCol.setCellValueFactory(cellData -> cellData.getValue()
					.isbnProperty());
			titleCol.setCellValueFactory(cellData -> cellData.getValue()
					.titlePropery());
			copyNumCol.setCellValueFactory(cellData -> cellData.getValue()
					.copyNumPropery());
			checkoutDateCol.setCellValueFactory(cellData -> cellData.getValue()
					.checkoutDatePropery());
			dueDateCol.setCellValueFactory(cellData -> cellData.getValue()
					.dueDatePropery());
		}else if (s[s.length - 1].equals("BookOverdue.fxml")) {
			isbnBOCol.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
			titleBOCol.setCellValueFactory(cellData -> cellData.getValue().titlePropery());
			memberIDBOCol.setCellValueFactory(cellData -> cellData.getValue().memberIDPropery());
			//checkoutDateBOCol.setCellValueFactory(cellData -> cellData.getValue()
			//		.checkoutDatePropery());
			dueDateBOCol.setCellValueFactory(cellData -> cellData.getValue()
					.dueDatePropery());

		}
	}

	@FXML
	private void handleloginBtn(MouseEvent event) {
		try {
			actionOnLogin();
		} catch (LoginException e) {
			log.warning(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void actionOnLogin() throws LoginException, IOException {
		String username = usernameTfd.getText();
		String password = passwordTfd.getText();

		log.info("user " + username);
		log.info("pass " + password);
		sysController = SystemController.getInstance();
		try {
			sysController.login(username, password);
		} catch (LoginException e) {
			promptLabel.setText(e.getMessage());
			throw e;
		}
		promptLabel.setText("");
		log.info("Successful login!");
		try {
			Stage stage = (Stage) promptLabel.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void handleLoginBtnOnKeyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginBtn.requestFocus();
			loginBtn.setEffect(new DropShadow());
			try {
				actionOnLogin();
			} catch (LoginException e) {
				loginBtn.setEffect(null);
				log.warning(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleResetBtn(MouseEvent event) {
		actionOnReset();
	}

	@FXML
	private void handleResetBtnOnKeyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			actionOnReset();
		}
	}

	private void actionOnReset() {
		usernameTfd.setText("");
		passwordTfd.setText("");
		promptLabel.setText("");
		loginBtn.setEffect(null);
	}

	/**
	 * Handle action related to Add Menu item.
	 *
	 * @param event
	 *            Event on "Add" menu item.
	 */
	@FXML
	private void handleAddLibraryMemberAction(final ActionEvent event) {
		addNewLibraryFunctionality();
	}

	/**
	 * Perform functionality associated with "Add New Library Member" menu
	 * selection or CTRL-A.
	 */
	// Stage stageAddNewLibarayMember = null;
	private void addNewLibraryFunctionality() {

		try {
			Stage stageAddNewLibarayMember = (Stage) menuBar.getScene()
					.getWindow();
			new AddNewMember().start(stageAddNewLibarayMember);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void closeNewMemberBtnAction() {
		commonCloseButtonHandler(closeNewMemberBtn);
	}

	@FXML
	private void saveNewMemberBtnAction() {
		Address address = new Address(memberStreetTfd.getText(),
				memberCityTfd.getText(), memberStateTfd.getText(),
				memberZipTfd.getText());
		try {
			SystemController.getInstance().addNewMember(memberIdTfd.getText(),
					memberFirstNameTfd.getText(), memberLastNameTfd.getText(),
					memberPhoneTfd.getText(), address);

			new PopupMessage("Successful !");
		} catch (LibrarySystemException ex) {
			new PopupMessage(ex.getMessage());
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Handle action related to Add Book to Menu item.
	 *
	 * @param event
	 *            Event on "Add" book menu item.
	 */
	@FXML
	private void handleAddBookAction(final ActionEvent event) {
		addNewBookFunctionality();
	}

	/**
	 * Handle action related to Add Book Copy Menu item.
	 *
	 * @param event
	 *            Event on "Add" Book Copy menu item.
	 */
	@FXML
	private void handleAddBookCopyAction(final ActionEvent event) {
		addNewBookCopyFunctionality();
	}

	// Section for Add Checkout Record

	@FXML
	private TextField memberForCheckoutRecordTfd;

	@FXML
	private TextField isbnCehckoutRecordTfd;

	@FXML
	private Button addCheckoutRecordBtn;

	@FXML
	private TableView<CheckoutRecordTableEntry> addCheckoutRecordTableView;

	@FXML
	private TableColumn<CheckoutRecordTableEntry, Integer>  copyNumAddCheckoutRecordColumn;

	@FXML
	private TableColumn<CheckoutRecordTableEntry, String>  dateAddCheckoutRecordColumn;

	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> dueDateAddCheckoutRecordColumn;

	/**
	 * Handle action related to Add Check Out Record to Menu item.
	 *
	 * @param event
	 *            Event on "Add" Check Out Record to menu item.
	 */
	@FXML
	private void handleAddCheckoutRecordAction(final ActionEvent event) {
		addCheckoutRecordFunctionality();
	}

	/**
	 * Perform functionality associated with "Add Checkout record" menu
	 * selection or CTRL-A.
	 */
	private void addCheckoutRecordFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new AddCheckoutRecord().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
	@FXML
	private void addCheckoutRecordBtnAction()
	{
		String memberId = memberForCheckoutRecordTfd.getText();
		String isbn = isbnCehckoutRecordTfd.getText();

		ObservableList<CheckoutRecordTableEntry> data = FXCollections.observableArrayList();

		try
		{
			SystemController.getInstance().checkoutBook(memberId, isbn);
			new PopupMessage("Successful!");

			CheckoutRecord checkoutRecord = SystemController.getInstance().getCheckoutRecordByMemberId(memberId);
			for(CheckoutRecordEntry entry : checkoutRecord.getEntries())
			{
				data.add(new CheckoutRecordTableEntry(entry));
			}

			addCheckoutRecordTableView.getItems().clear();
			addCheckoutRecordTableView.setItems(data);

	        copyNumAddCheckoutRecordColumn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordTableEntry, Integer>("copyNum"));
	        dateAddCheckoutRecordColumn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordTableEntry, String>("checkoutDate"));
	        dueDateAddCheckoutRecordColumn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordTableEntry, String>("dueDate"));
		}
		catch(LibrarySystemException ex)
		{
			new PopupMessage(ex.getMessage());
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@FXML
	private Button closeAddCheckoutRecordBtn;

	@FXML
	private void closeAddCheckoutRecordBtnAction()
	{
		commonCloseButtonHandler(closeAddCheckoutRecordBtn);
	}


	// Section for printing Check out record
	/**
	 * Handle action related to Print Check Out Record to Menu item.
	 *
	 * @param event
	 *            Event on "Print" Check Out Record to menu item.
	 */
	@FXML
	private void handlePrintCheckoutRecordAction(final ActionEvent event) {
		printCheckoutRecordFunctionality();
	}

	/**
	 * Handle action related to Search Book Overdue to Menu item.
	 *
	 * @param event
	 *            Event on "Search" Book Overdue to menu item.
	 */
	@FXML
	private void handleSearchBookOverdueAction(final ActionEvent event) {
		searchBookOverdueFunctionality();
	}

	/**
	 * Perform functionality associated with "Add New Book" menu selection or
	 * CTRL-A.
	 */
	private void addNewBookFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new AddNewBook().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Perform functionality associated with "Add New Book Copy" menu selection
	 * or CTRL-A.
	 */
	private void addNewBookCopyFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new AddNewBookCopy().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * Dung Le: handle saving a new book copy
	 */

	@FXML
	private void saveNewBookCopyBtnAction() {
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if (b == null) {
			new PopupMessage("This book does not exist in our library!");
		} else {
			try {
				SystemController.getInstance().addBookCopy(
						isbn.getText().trim());

				new PopupMessage("Successful !");
			} catch (LibrarySystemException ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * Dung Le: handle close form of saving a new book copy
	 */

	@FXML
	private void closeNewBookCopyBtnAction() {
		commonCloseButtonHandler(closeNewBookCopyBtn);
	}

	/*
	 * Dung Le: handle saving a new book
	 */

	@FXML
	private void saveNewBookBtnAction() {
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if(b == null) {
			List<Author> selectedItems = authorListView.getSelectionModel()
					.getSelectedItems();
			try {
				SystemController.getInstance().addBook(isbn.getText(),
						title.getText(), maxcheckoutlength.getText(),
						numofcopies.getText(), selectedItems);
				int numCopies = Integer.parseInt(numofcopies.getText());
				for (int i = 0; i < numCopies; i++) {
					SystemController.getInstance().addBookCopy(
							isbn.getText().trim());
				}
				new PopupMessage("Successful !");
			} catch (LibrarySystemException ex) {
				ex.printStackTrace();
			}
		} else {
			new PopupMessage("This book exists in our library!");
		}
	}

	/*
	 * Dung Le: handle close form of saving a new book
	 */

	@FXML
	private void closeNewBookBtnAction() {
		commonCloseButtonHandler(closeNewBookBtn);
	}

	/*
	 * Dung Le: handle print checkout
	 */

	@FXML
	private void printCheckoutBtnAction() {
		ObservableList<CheckoutRecordTableEntry> listData = FXCollections
				.observableArrayList((SystemController.getInstance().getCheckoutTableEntryByMemberId(memberID.getText().trim())));
		checkoutsView.setItems(listData);// if (b == null) {
	}


	/*
	 * Dung Le: handle close form of print checkout
	 */

	@FXML
	private void closePrintCheckoutBtnAction() {
		commonCloseButtonHandler(closePrintCheckoutBtn);
	}

	/*
	 * Dung Le: handle check book overdue
	 */

	@FXML
	private void checkBookOverDueBtnAction() {
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if (b == null) {
			new PopupMessage("This book does not exist in our library!");
		} else {
			try {
				SystemController.getInstance().addBookCopy(
						isbn.getText().trim());

				new PopupMessage("Successful !");
			} catch (LibrarySystemException ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * Dung Le: handle close form of check book overdue
	 */

	@FXML
	private void closeCheckBookOverDueBtnAction() {
		commonCloseButtonHandler(closeCheckBookOverDueBtn);
	}



	/**
	 * Perform functionality associated with "Print Checkout Record" menu
	 * selection or CTRL-A.
	 */
	private void printCheckoutRecordFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new PrintCheckoutRecord().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Perform functionality associated with "Search Book Overdue" menu
	 * selection or CTRL-A.
	 */
	private void searchBookOverdueFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new SearchBookOverdue().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	// For Edit Library Member
	private void disableEditLibraryPartial(boolean isDisable) {
		saveEditMemberBtn.setDisable(isDisable);
		editMemberIdTfd.setDisable(isDisable);
		editMemberFirstNameTfd.setDisable(isDisable);
		editMemberLastNameTfd.setDisable(isDisable);
		editMemberPhoneTfd.setDisable(isDisable);
		editMemberStreetTfd.setDisable(isDisable);
		editMemberCityTfd.setDisable(isDisable);
		editMemberStateTfd.setDisable(isDisable);
		editMemberZipTfd.setDisable(isDisable);
	}

	/**
	 * Handle action related to Edit Library Member to Menu item.
	 *
	 * @param event
	 *            Event on "Edit" Library Member menu item.
	 */
	@FXML
	private void handleEditLibraryMemberAction(final ActionEvent event) {
		editLibraryMemberFunctionality();
	}

	@FXML
	private void closeEditMemberBtnAction() {
		commonCloseButtonHandler(closeEditMemberBtn);
	}

	/**
	 * Perform functionality associated with "Edit Library Member" menu
	 * selection.
	 */
	private void editLibraryMemberFunctionality() {
		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new EditLibraryMember().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void editLibraryMemberSearchBtnAction() {
		LibraryMember libraryMember = isMemberExisted(editMemberIdTfd.getText());

		if (libraryMember != null) {
			editMemberFirstNameTfd.setText(libraryMember.getFirstName());
			editMemberLastNameTfd.setText(libraryMember.getLastName());
			editMemberPhoneTfd.setText(libraryMember.getTelephone());
			editMemberStreetTfd.setText(libraryMember.getAddress().getStreet());
			editMemberCityTfd.setText(libraryMember.getAddress().getCity());
			editMemberStateTfd.setText(libraryMember.getAddress().getState());
			editMemberZipTfd.setText(libraryMember.getAddress().getZip());
			disableEditLibraryPartial(false);
		} else {
			new PopupMessage("Invalid Member");
		}
	}

	@FXML
	private void saveEditMemberBtnAction() {
		Address address = new Address(editMemberStreetTfd.getText(),
				editMemberCityTfd.getText(), editMemberStateTfd.getText(),
				editMemberZipTfd.getText());

		LibraryMember libraryMember = new LibraryMember(
				editMemberIdTfd.getText(), editMemberFirstNameTfd.getText(),
				editMemberLastNameTfd.getText(), editMemberPhoneTfd.getText(),
				address);

		try {
			SystemController.getInstance().updateMemberInfo(libraryMember);
			Stage stage = (Stage) editLibraryMemberSearchBtn.getScene()
					.getWindow();
			new PopupMessage("Successful !");
			stage.close();
		} catch (LibrarySystemException ex) {
			new PopupMessage(ex.getMessage());
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private LibraryMember isMemberExisted(String memberId) {
		LibraryMember libraryMember = null;
		libraryMember = SystemController.getInstance().searchMember(memberId);

		return libraryMember;
	}
}
