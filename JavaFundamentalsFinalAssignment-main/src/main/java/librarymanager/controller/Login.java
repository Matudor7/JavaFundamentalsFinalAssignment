package librarymanager.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import librarymanager.Main;
import librarymanager.data.Database;
import librarymanager.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Login implements Initializable {
    Database db;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    @FXML
    Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
    }

    public void loginButton(ActionEvent event) throws IOException, ClassNotFoundException {
        db = new Database();
        User loggingUser = new User(usernameTextField.getText(), passwordField.getText());

        List<User> validUsers = db.GetAllUsers();

        for(User user : validUsers){
            if(loggingUser.getUsername().equals(user.getUsername())){
                if(loggingUser.getPassword().equals(user.getPassword())){
                    LoadMainWindow(event, user);
                    break;
                }
            } else{
                errorLabel.setText("Invalid username or password");
            }
        }
    }

    public void LoadMainWindow(ActionEvent event, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../../resources/MainWindow.fxml"));

        MainWindow mainWindowController = new MainWindow();
        fxmlLoader.setController(mainWindowController);
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        mainWindowController.DisplayWelcomeMessage(user.getName());
    }

}
