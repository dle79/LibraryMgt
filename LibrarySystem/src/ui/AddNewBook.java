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

public class AddNewBook extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = null;
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("AddNewBook.fxml"));
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			dialogStage.setScene(scene);
			ListView authorListView = (ListView) scene.lookup("#authorListView");
			dialogStage.setResizable(false);
			List<Author> allAuthors = new ArrayList<Author>() ;
			allAuthors.add(new Author("Joe", "Thomas", "641-445-2123", new Address("101 S. Main", "Fairfield", "IA", "52556"), "A happy man is he."));
			allAuthors.add(new Author("Sandra", "Thomas", "641-445-2123", new Address("51 S. George", "Georgetown", "MI", "65434"), "A happy wife is she."));
			allAuthors.add(new Author("Nirmal", "Pugh", "641-919-3223", new Address("23 Headley Ave", "Seville", "Georgia", "41234"), "Thinker of thoughts."));
			allAuthors.add(new Author("Andrew", "Cleveland", "976-445-2232", new Address("1 N. Baton", "Baton Rouge", "LA", "33556"), "Author of childrens' books."));
			allAuthors.add(new Author("Sarah", "Connor", "123-422-2663", new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"), "Known for her clever style."));
			ObservableList<Author> authors = FXCollections.observableArrayList(allAuthors);//SystemController.getInstance().authors.getList());
			
			authorListView.setItems(authors);
			authorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}