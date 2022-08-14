package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField usernameField;
    public AnchorPane loginWindow;

    public void loginOnAction(ActionEvent actionEvent) {
        if (!usernameField.getText().isEmpty()){
            ClientFormController.username = usernameField.getText();
            Stage stage = (Stage) loginWindow.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/ClientForm.fxml"))));
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setTitle(usernameField.getText() + " | Play Tech Chat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
