package ui;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
	
	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		showLoginForm();

	}

	public void showLoginForm()
	{
		/*
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
			dialogStage.setTitle("Login");
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
		}*/
		  Scene scene = null;
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			dialogStage.setScene(scene);
			dialogStage.setTitle("Login");
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
