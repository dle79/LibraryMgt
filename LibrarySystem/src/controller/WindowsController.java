package controller;

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
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class WindowsController implements Initializable {

	private static final Logger log = Logger.getLogger(WindowsController.class.getName());
	private static final String LOG_HEADER = "======= loginForm " + WindowsController.class.getName() + "=======";

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
			e.printStackTrace();
		}
	}

	private void actionOnLogin() throws LoginException {
		String username = usernameTfd.getText();
		String password = passwordTfd.getText();
		log.info(LOG_HEADER);
		log.info("user " + username);
		log.info("pass " + password);
		sysController = SystemController.getInstance();
		try {
			sysController.login(username, password);
		} catch (LoginException e) {
			log.warning(LOG_HEADER + e.getMessage());
			promptLabel.setText(e.getMessage());
			throw e;
		}
		promptLabel.setText("");
		log.info(LOG_HEADER + " successful login!");
	}

	@FXML
	private void handleLoginBtnOnKeyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginBtn.requestFocus();
			try {
				actionOnLogin();
			} catch (LoginException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	private void handleResetBtn(MouseEvent event) {
		usernameTfd.setText("");
		passwordTfd.setText("");
		promptLabel.setText("");
	}

}
