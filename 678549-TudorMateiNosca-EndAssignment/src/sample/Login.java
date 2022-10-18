package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.awt.event.WindowListener;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = fxmlLoader.load();

        MainWindow mainWindowController = fxmlLoader.getController();
        mainWindowController.DisplayWelcomeMessage(user.name);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
