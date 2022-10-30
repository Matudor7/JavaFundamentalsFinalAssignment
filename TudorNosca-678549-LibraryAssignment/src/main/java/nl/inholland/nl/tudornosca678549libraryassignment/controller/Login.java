package nl.inholland.nl.tudornosca678549libraryassignment.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.nl.tudornosca678549libraryassignment.Main;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class Login implements Initializable{
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

        List<User> validUsers = db.getAllUsers();

        for(User user : validUsers){
            if(loggingUser.getUsername().equals(user.getUsername())){
                if(loggingUser.getPassword().equals(user.getPassword())){
                    loadMainWindow(event, user);
                    break;
                }
            } else{
                errorLabel.setText("Invalid username or password");
            }
        }
    }

    public void loadMainWindow(ActionEvent event, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));

        MainWindow mainWindowController = new MainWindow(db);
        fxmlLoader.setController(mainWindowController);
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        mainWindowController.displayWelcomeMessage(user.getName());

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    db.serializeObjects();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.exit();
            }
        });
    }
}
