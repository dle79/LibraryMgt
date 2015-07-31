package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import ui.Contact;
import ui.EditLibraryMember;
import ui.PopupMessage;
import ui.PrintCheckoutRecord;
import ui.SearchBookOverdue;
import business.Address;
import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.CheckoutRecordTableEntry;
import business.CopyStatus;
import business.LibraryMember;
import dataaccess.Auth;
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
	private TableView<CheckoutRecordTableEntry> boDuesView;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> isbnBOCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> titleBOCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> memberIDBOCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> copyNumBOCol;
	@FXML
	private TableColumn<CheckoutRecordTableEntry, String> dueDateBOCol;


	@FXML
	private Button closeCheckBookOverDueBtn;

	@FXML
	private MenuItem checkoutRecordMenuItem;

	@FXML
	private Menu addMenu;

	@FXML
	private MenuItem checkoutMenuItem;

	@FXML
	private MenuItem overdueMenuItem;

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
			copyNumBOCol.setCellValueFactory(cellData -> cellData.getValue()
					.copyNumPropery());
			memberIDBOCol.setCellValueFactory(cellData -> cellData.getValue().memberIDPropery());
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

	@FXML
	private void handleMenuItemAction(ActionEvent event){
		Auth role = sysController.getCurrentAuth();
		if(role == Auth.LIBRARIAN){
		checkoutRecordMenuItem.setDisable(true);
		checkoutMenuItem.setDisable(true);
		overdueMenuItem.setDisable(true);
		}
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
		if(memberStreetTfd.getText().isEmpty() || 
				memberCityTfd.getText().isEmpty() || 
				memberStateTfd.getText().isEmpty() ||
				memberZipTfd.getText().isEmpty() ||
				memberIdTfd.getText().isEmpty() ||
				memberFirstNameTfd.getText().isEmpty() ||
				memberLastNameTfd.getText().isEmpty() ||
				memberPhoneTfd.getText().isEmpty()){
				new PopupMessage("Please input data!");
				return;
		}
		Address address = new Address(memberStreetTfd.getText(),
				memberCityTfd.getText(), memberStateTfd.getText(),
				memberZipTfd.getText());
		try {
			SystemController.getInstance().addNewMember(memberIdTfd.getText(),
					memberFirstNameTfd.getText(), memberLastNameTfd.getText(),
					memberPhoneTfd.getText(), address);

			new PopupMessage("Successful !");
			clearTextFieldMember();
		} catch (LibrarySystemException ex) {
			new PopupMessage(ex.getMessage());
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}
	private void clearTextFieldMember()
	{
		memberIdTfd.setText("");
		memberCityTfd.setText("");
		memberFirstNameTfd.setText("");
		memberLastNameTfd.setText("");
		memberPhoneTfd.setText("");
		memberStreetTfd.setText("");
		memberCityTfd.setText("");
		memberStateTfd.setText("");
		memberZipTfd.setText("");
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
	private TableColumn<CheckoutRecordTableEntry, Integer>  titleAddCheckoutRecordColumn;
	
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
		
		String memberId = memberForCheckoutRecordTfd.getText().trim();
		String isbn = isbnCehckoutRecordTfd.getText().trim();
		if(memberId.isEmpty()|| isbn.isEmpty()){
			new PopupMessage("Please input the information!");
			return;
		}
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

			titleAddCheckoutRecordColumn.setCellValueFactory(new PropertyValueFactory<CheckoutRecordTableEntry, Integer>("title"));
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
		if(isbn.getText().trim().isEmpty()){
			new PopupMessage("Please input the isbn!");
			return;
		}
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if (b == null) {
			new PopupMessage("This book does not exist in our library!");
		} else {
			try {
				SystemController.getInstance().addBookCopy(
						isbn.getText().trim());

				new PopupMessage("Successful !");
				isbn.setText("");
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
		if(isbn.getText().trim().trim().isEmpty() ||
			authorListView.getSelectionModel().getSelectedItems() == null ||
			maxcheckoutlength.getText().trim().isEmpty() ||
			numofcopies.getText().trim().isEmpty() ||			
			title.getText().trim().isEmpty()){
			new PopupMessage("Please input the information !");
			return;
		}
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if(b == null) {
			List<Author> selectedItems = authorListView.getSelectionModel()
					.getSelectedItems();
			Author[] arr = selectedItems.toArray(new Author[] {});

			try {
				SystemController.getInstance().addBook(isbn.getText(),
						title.getText(), maxcheckoutlength.getText(),
						numofcopies.getText(), arr);
				new PopupMessage("Successful !");
				clearAddNewBookForm();
			} catch (LibrarySystemException ex) {
				ex.printStackTrace();
			}
		} else {
			new PopupMessage("This book exists in our library!");
		}
	}

	private void clearAddNewBookForm()
	{
		authorListView.getSelectionModel().clearSelection();
		maxcheckoutlength.setText("");
		numofcopies.setText("");
		isbn.setText("");
		title.setText("");

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
		if(memberID.getText().trim().isEmpty()){
			new PopupMessage("Please input the member ID!");
			return;
		}
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
		if(isbn.getText().trim().isEmpty()){
			new PopupMessage("Please input the isbn!");
			return;
		}
		Book b = SystemController.getInstance().searchBook(
				isbn.getText().trim());
		if (b == null) {
			new PopupMessage("This book does not exist in our library!");
		} else {
			try {
				List<CopyStatus> copyStatuslist = SystemController.getInstance().getCopyStatusListByISBN(isbn.getText().trim());
				List<CheckoutRecordTableEntry> entries = new ArrayList<CheckoutRecordTableEntry>();
				for(CopyStatus cs : copyStatuslist){
					entries.add(new CheckoutRecordTableEntry(cs.getIsbn(), cs.getTitle(), cs.getCopyNum(), cs.getMemberName(), cs.getDueBack()));
				}
				ObservableList<CheckoutRecordTableEntry> listData = FXCollections
						.observableArrayList(entries);
				boDuesView.setItems(listData);

				if(listData.isEmpty()){
					new PopupMessage("This book has noThere are no book in overdue !");
				}else{
					new PopupMessage("Successful !");
				}
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
		if(editMemberIdTfd.getText().trim().isEmpty()){
			new PopupMessage("Please input the Member ID");
			return;
		}
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

	/**
	 * Handle action related to Add Check Out Record to Menu item.
	 *
	 * @param event
	 *            Event on "Add" Check Out Record to menu item.
	 */
	@FXML
	private void handleContactAction(final ActionEvent event) {
		contactFunctionality();
	}

	/**
	 * Perform functionality associated with "Add Checkout record" menu
	 * selection or CTRL-A.
	 */
	private void contactFunctionality() {

		try {
			Stage stage = (Stage) menuBar.getScene().getWindow();
			new Contact().start(stage);

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}


}
