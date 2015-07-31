package ui;


import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibrarySystem extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("LibrarySystem.fxml"));

			primaryStage.setTitle("MUM Library Management System");
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
	}
}

