package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import business.Address;
import business.LibraryMember;
import ui.AddNewMember;
import exception.LibrarySystemException;
import exception.LoginException;
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
	 * Handle action related to input (in this case specifically only responds
	 * to keyboard event CTRL-A).
	 * 
	 * @param event
	 *            Input event.
	 */
	@FXML
	private void handleKeyInput(final InputEvent event) {
		if (event instanceof KeyEvent) {
			final KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A) {
				addNewLibraryFunctionality();
			}
		}
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
}
