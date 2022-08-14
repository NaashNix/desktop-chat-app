package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public static String username;

    public TextField messageTextField;
    public ScrollPane sp_main;
    public Label usernameLabel;
    public VBox vb_main;
    public AnchorPane clientContext;
    private Client client;

    public void sendOnAction(ActionEvent actionEvent) {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            sendMessage(message);
            messageTextField.clear();
            client.clientSendMessage(message);
//            textField.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (username != null){
            usernameLabel.setText(username);
        }else {
            System.err.println("username is null");
        }
//        usernameLabel.setText(username);
        try {
            client = new Client(new Socket("localhost", 9000), username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientContext.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });
        client.listenForMessage(vb_main);
        client.clientSendMessage("");
    }

    public static void receiveMessage(String message, VBox vBox){
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #95a5a6;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

    public void sendMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #3498db;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vb_main.getChildren().add(hBox);
    }

}
