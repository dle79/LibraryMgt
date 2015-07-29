package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.AddCheckoutRecord;
import ui.AddNewBook;
import ui.AddNewBookCopy;
import ui.AddNewMember;
import ui.EditLibraryMember;
import ui.PrintCheckoutRecord;
import business.Address;
import exception.LibrarySystemException;
import exception.LoginException;

public class WindowsController implements Initializable {

	private static final Logger log = Logger.getLogger(WindowsController.class.getName());

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
	
	
	private void commonCloseButtonHandler(Button closeButton)
	{
		// get a handle to the stage
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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
	 * Perform functionality associated with "Add New Library Member" menu selection or CTRL-A.
	 */
	//Stage stageAddNewLibarayMember = null;
	private void addNewLibraryFunctionality() {
		
		try {
			Stage stageAddNewLibarayMember = (Stage) menuBar.getScene().getWindow();
			new AddNewMember().start(stageAddNewLibarayMember);
			
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void closeNewMemberBtnAction()
	{
	    commonCloseButtonHandler(closeNewMemberBtn);
	}
	
	@FXML
	private void saveNewMemberBtnAction()
	{
		Address address = new Address(memberStreetTfd.getText(), 
				memberCityTfd.getText(), 
				memberStateTfd.getText(), 
				memberZipTfd.getText());
		try
		{
			SystemController.getInstance().addNewMember(memberIdTfd.getText(), 
					memberFirstNameTfd.getText(),
					memberLastNameTfd.getText(),
					memberPhoneTfd.getText(), address);
			
			commonCloseButtonHandler(saveNewMemberBtn);
		}
		catch(LibrarySystemException ex)
		{
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
	 * Handle action related to Add Check Out Record to Menu item.
	 * 
	 * @param event
	 *            Event on "Add" Check Out Record to menu item.
	 */
	@FXML
	private void handlePrintCheckoutRecordAction(final ActionEvent event) {
		printCheckoutRecordFunctionality();
	}
	
	/**
	 * Perform functionality associated with "Add New Book" menu selection or CTRL-A.
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
	 * Perform functionality associated with "Add New Book Copy" menu selection or CTRL-A.
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

	/**
	 * Perform functionality associated with "Add Checkout record" menu selection or CTRL-A.
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
	
	/**
	 * Perform functionality associated with "Add Checkout record" menu selection or CTRL-A.
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

	// For Edit Library Member
	private void disableEditLibraryPartial(boolean isDisable)
	{	
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
	private void closeEditMemberBtnAction()
	{
		commonCloseButtonHandler(closeEditMemberBtn);
	}
	/**
	 * Perform functionality associated with "Edit Library Member" menu selection.
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
	private void editLibraryMemberSearchBtnAction()
	{
		disableEditLibraryPartial(false);
	}
}
