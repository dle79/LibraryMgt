package ui;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PopupMessage {
	
	public PopupMessage(String message, Stage primaryStage)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText(message);
		alert.showAndWait();
	}
}
