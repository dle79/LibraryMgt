package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WindowsController implements Initializable {

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
		// Call SystemController login method
	}

	@FXML
	private void handleResetBtn(MouseEvent event) {
		usernameTfd.setText("");
		passwordTfd.setText("");
	}

}
