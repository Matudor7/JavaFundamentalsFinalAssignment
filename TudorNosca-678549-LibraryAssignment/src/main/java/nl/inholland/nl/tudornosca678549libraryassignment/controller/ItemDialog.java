package nl.inholland.nl.tudornosca678549libraryassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDialog implements Initializable {

    Database database;

    Item item;

    MainWindow mainWindow;
    @FXML
    TextField titleTextField;

    @FXML
    Label errorLabel;

    @FXML
    Label promptLabel;

    @FXML
    TextField authorTextField;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Button cancelButton;

    public ItemDialog(Database database, Item item, MainWindow mainWindow){
        this.database = database;
        this.item = item;
        this.mainWindow = mainWindow;
    }

    public void onAddButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        try{
            if(titleTextField.getText().isEmpty() || authorTextField.getText().isEmpty()){
                errorLabel.setText("You must fill both fields to add a new item.");
            }else{
                database.WriteItemToDatabase(new Item(true, titleTextField.getText(), authorTextField.getText()));
                mainWindow.LoadCollectionTableView();
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        catch(Exception e){
            errorLabel.setText("You must fill both fields to add a new item.");
        }
    }

    public void onEditButtonClick(ActionEvent event) throws IOException, ClassNotFoundException{
        try{
            if(titleTextField.getText().isEmpty() || authorTextField.getText().isEmpty()){
                errorLabel.setText("You must fill both fields to add a new item.");
            }else{
                database.editItem(item, new Item(item.getIsAvailable(), titleTextField.getText(), authorTextField.getText()));
                mainWindow.LoadCollectionTableView();
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }catch(Exception e){

        }
    }

    public void onCancelButtonClick(ActionEvent event){
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");

        if(item == null){
            promptLabel.setText("Add an item");
            editButton.setVisible(false);
            addButton.setVisible(true);
        }else{
            promptLabel.setText("Edit an item");
            addButton.setVisible(false);
            editButton.setVisible(true);

            titleTextField.setText(item.getTitle());
            authorTextField.setText(item.getAuthor());
        }
    }
}
