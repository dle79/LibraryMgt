package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddNewBookCopy extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = null;
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("AddNewBookCopy.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add new book copy");
			dialogStage.setResizable(false);
			
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}