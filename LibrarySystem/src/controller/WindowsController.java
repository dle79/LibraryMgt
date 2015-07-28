package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import exception.LoginException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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

}
