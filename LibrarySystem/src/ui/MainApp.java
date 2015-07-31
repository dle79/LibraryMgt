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
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MUM Library Management System");
		
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("LibrarySystem.fxml"));
			File file = new File(System.getProperty("user.dir") + "/src/ui/library-pic.jpg");

			Image image = new Image(file.toURI().toURL().toExternalForm());
			// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
			BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
			// new BackgroundImage(image, repeatX, repeatY, position, size)
			BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			// new Background(images...)
			Background background = new Background(backgroundImage);			
			root.setBackground(background);

			Scene scene = new Scene(root);
			File fileIcon = new File(System.getProperty("user.dir") + "/src/ui/library_icon.jpg");

			Image imageIcon = new Image(fileIcon.toURI().toURL().toExternalForm());

			primaryStage.getIcons().add(imageIcon);
			scene.getStylesheets().add(getClass().getResource("applicationMain.css").toExternalForm());
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
