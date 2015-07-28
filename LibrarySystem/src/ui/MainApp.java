package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MUM Library Management System");
		
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("LibrarySystem.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		showLoginForm();

	}

	public void showLoginForm()
	{
		VBox root = null;
		Scene scene = null;
		try {
			root = (VBox)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			dialogStage.setScene(scene);
			
			dialogStage.setResizable(false);
			dialogStage.setOnCloseRequest(new EventHandler(){

				@Override
				public void handle(Event event) {
					// TODO Auto-generated method stub
					Platform.exit();
				}
				
			});
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
