package ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import business.Address;
import business.Author;

public class Contact extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = null;
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("Contact.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			scene = new Scene(root);
			
			dialogStage.setScene(scene);
			dialogStage.setTitle("Contact information");
			dialogStage.setResizable(false);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}