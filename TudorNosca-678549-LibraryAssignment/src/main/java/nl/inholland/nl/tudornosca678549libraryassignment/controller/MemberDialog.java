package nl.inholland.nl.tudornosca678549libraryassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Member;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MemberDialog implements Initializable {

    Database database;

    MainWindow mainWindow;

    Member member;

    @FXML
    Label titleLabel;

    @FXML
    Label errorLabel;

    @FXML
    TextField firstNameTextField;

    @FXML
    TextField lastNameTextField;

    @FXML
    DatePicker birthDatePicker;

    @FXML
    Button addMemberButton;

    @FXML
    Button editMemberButton;

    @FXML
    Button cancelButton;

    public MemberDialog(Database database, Member member, MainWindow mainWindow){
        this.database = database;
        this.member = member;
        this.mainWindow = mainWindow;
    }

    public void onAddButtonClick(ActionEvent event){
        try{
            if(firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || (birthDatePicker.getEditor().getText().isEmpty() && birthDatePicker.getValue() == null)){
                errorLabel.setText("You must fill out all fields to add a new member");
            }else{
                if(birthDatePicker.getValue() == null){
                    database.writeMemberToDatabase(new Member(firstNameTextField.getText(), lastNameTextField.getText(), LocalDate.parse(birthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                }else{
                    database.writeMemberToDatabase(new Member(firstNameTextField.getText(), lastNameTextField.getText(), birthDatePicker.getValue()));
                }
                mainWindow.loadMembersTableView();

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }catch(Exception e){
            errorLabel.setText("Wrong date format entered");
        }
    }

    public void onEditButtonClick(ActionEvent event){
        try{
            if(firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || (birthDatePicker.getEditor().getText().isEmpty() && birthDatePicker.getValue() == null)){
                errorLabel.setText("You must fill out all fields to edit the member");
            }else{
                if(birthDatePicker.getValue() == null){
                    database.editMember(member, new Member(firstNameTextField.getText(), lastNameTextField.getText(), LocalDate.parse(birthDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                }else{
                    database.editMember(member, new Member(firstNameTextField.getText(), lastNameTextField.getText(), birthDatePicker.getValue()));
                }
                mainWindow.loadMembersTableView();

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }catch(Exception e){
            errorLabel.setText("Wrong date format entered");
        }
    }

    public void onCancelButtonClick(ActionEvent event){
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");

        if(member == null){
            titleLabel.setText("Add a member");
            editMemberButton.setVisible(false);
            addMemberButton.setVisible(true);
        }else {
            titleLabel.setText("Edit a member");
            addMemberButton.setVisible(false);
            editMemberButton.setVisible(true);

            firstNameTextField.setText(member.getFirstName());
            lastNameTextField.setText(member.getLastName());
            birthDatePicker.getEditor().setText(member.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
}
