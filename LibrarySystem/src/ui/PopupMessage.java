package ui;

import java.awt.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupMessage {
	
	String message;
	public PopupMessage(String message, Stage primaryStage)
	{
		this.message = message;

        final Stage dialog = new Stage();
        dialog.setTitle("Confirm Before Exit");
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);

        FlowPane buttons = new FlowPane(10,10);
        buttons.setAlignment(Pos.CENTER);
        Button ok = new Button("Ok");
        buttons.getChildren().addAll(ok);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        Label msgInfo = new Label(message);
        msgInfo.setTextFill(Color.RED);
  
        box.getChildren().addAll(msgInfo, buttons);
         Scene s = new Scene(box);
        dialog.setScene(s);
        dialog.show();

	}
}
