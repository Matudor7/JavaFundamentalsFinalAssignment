package nl.inholland.nl.tudornosca678549libraryassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.nl.tudornosca678549libraryassignment.Main;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Item;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Member;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
public class MainWindow implements Initializable {
    Database db;

    @FXML
    Label welcomeLabel;

    @FXML
    Label lendErrorLabel;

    @FXML
    Label receiveErrorLabel;

    @FXML
    Label lendStatusLabel;

    @FXML
    Label receiveStatusLabel;

    @FXML
    Button lendingReceivingButton;

    @FXML
    Button collectionButton;

    @FXML
    Button membersButton;

    @FXML
    Pane lendingReceivingPane;

    @FXML
    Pane membersPane;

    @FXML
    Pane collectionPane;

    @FXML
    Button lendButton;

    @FXML
    Button receiveButton;

    @FXML
    TextField itemCodeLendTextField;

    @FXML
    TextField memberCodeTextField;

    @FXML
    TextField itemCodeReceiveTextField;

    @FXML
    TableView<Item> collectionTableView;

    @FXML
    TableColumn itemCodeColumn;

    @FXML
    TableColumn itemAvailableColumn;

    @FXML
    TableColumn itemTitleColumn;

    @FXML
    TableColumn itemAuthorColumn;

    @FXML
    Button addItemButton;

    @FXML
    Button editItemButton;

    @FXML
    Button deleteItemButton;

    @FXML
    TableView<Member> membersTableView;

    @FXML
    TableColumn memberIdentifierColumn;

    @FXML
    TableColumn memberFirstNameColumn;

    @FXML
    TableColumn memberLastNameColumn;

    @FXML
    TableColumn memberBirthDateColumn;

    @FXML
    Button addMemberButton;

    @FXML
    Button editMemberButton;

    @FXML
    Button deleteMemberButton;

    public MainWindow(Database database){this.db = database;}

    public void displayWelcomeMessage(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void onLendingReceivingButtonClick() {
        lendingReceivingPane.setVisible(true);
        membersPane.setVisible(false);
        collectionPane.setVisible(false);
    }

    public void onCollectionButtonClick(){
        lendingReceivingPane.setVisible(false);
        membersPane.setVisible(false);
        collectionPane.setVisible(true);

        loadCollectionTableView();
    }

    public void onMembersButtonClick(){
        lendingReceivingPane.setVisible(false);
        membersPane.setVisible(true);
        collectionPane.setVisible(false);

        loadMembersTableView();
    }

    public void onLendButtonClick(){
        try{
            if(itemCodeLendTextField.getText().isEmpty() || memberCodeTextField.getText().isEmpty()){
                lendErrorLabel.setText("You must fill both fields to lend item.");
                lendStatusLabel.setText("");
            }else{
                Item itemToLend = db.getItemByCode(Integer.parseInt(itemCodeLendTextField.getText()));
                Member memberLending = db.getMemberByCode(Integer.parseInt(memberCodeTextField.getText()));

                if(itemToLend == null || memberLending == null){
                    lendErrorLabel.setText("One of the fields has an incorrect value.");
                    lendStatusLabel.setText("");
                }else{
                    if(itemToLend.getIsAvailable()){
                        db.lendItem(itemToLend);
                        lendStatusLabel.setText("Successfully lended " + itemToLend.getTitle() + " to " + memberLending.getFirstName() + " " + memberLending.getLastName() + ".");
                        lendErrorLabel.setText("");
                    }else{
                        lendErrorLabel.setText("Item is not available to lend.");
                        lendStatusLabel.setText("");
                    }
                }
            }
        }catch(Exception e){
            lendErrorLabel.setText("Invalid input.");
            lendStatusLabel.setText("");
        }
    }

    public void onReceiveButtonClick(){
        try{
            if(itemCodeReceiveTextField.getText().isEmpty()){
                receiveErrorLabel.setText("Code is invalid.");
                receiveStatusLabel.setText("");
            }else{
                Item itemToReceive = db.getItemByCode(Integer.parseInt(itemCodeReceiveTextField.getText()));

                if(itemToReceive == null){
                    receiveErrorLabel.setText("Code is invalid.");
                    receiveStatusLabel.setText("");
                }

                if(itemToReceive.getIsAvailable()){
                    receiveErrorLabel.setText("Item has not been lent.");
                    receiveStatusLabel.setText("");
                }else{
                    LocalDate now = LocalDate.now();
                    Period between = Period.between(itemToReceive.getTimeLent(), now);

                    if(between.getDays() < 21 && between.getMonths() == 0 && between.getYears() == 0){
                        db.receiveItem(itemToReceive);
                        receiveStatusLabel.setText("Received " + itemToReceive.getTitle() + " back.");
                        receiveErrorLabel.setText("");
                    }else{
                        if(between.getMonths() > 0){
                            if(between.getYears() > 0){
                                receiveStatusLabel.setText("Item is too late to be received. (Late by " + between.getDays() + "days, " + between.getMonths() + " months and" + between.getYears() + " years).");
                            }else{
                                receiveStatusLabel.setText("Item is too late to be received. (Late by " + between.getDays() + "days, and " + between.getMonths() + " months).");
                            }
                        }else{
                            receiveStatusLabel.setText("Item is too late to be received. (Late by " + between.getDays() + "days).");
                        }
                    }
                }
            }
        }catch(Exception e){
            receiveErrorLabel.setText("Code is invalid");
            receiveStatusLabel.setText("");
        }
    }

    public void onAddItemButtonClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("itemDialog.fxml"));
            ItemDialog itemDialog = new ItemDialog(db, null, this);
            fxmlLoader.setController(itemDialog);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add item");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onEditItemButtonClick(){
        try{
            if(collectionTableView.getSelectionModel().getSelectedItem() != null){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("itemDialog.fxml"));
                ItemDialog itemDialog = new ItemDialog(db, collectionTableView.getSelectionModel().getSelectedItem(), this);
                fxmlLoader.setController(itemDialog);
                Scene scene = new Scene(fxmlLoader.load());

                Stage dialog = new Stage();
                dialog.setScene(scene);
                dialog.setTitle("Edit item");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.showAndWait();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onDeleteItemButtonClick(){
        try{
            if(collectionTableView.getSelectionModel().getSelectedItem() != null){
                db.deleteItem(collectionTableView.getSelectionModel().getSelectedItem());
                loadCollectionTableView();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void loadCollectionTableView(){
        List<Item> items = db.getAllItems();

        collectionTableView.getItems().clear();

        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("availabilityString"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        for (Item item : items) {
            if (item.getIsAvailable()) {
                item.setAvailabilityString("Yes");
            } else {
                item.setAvailabilityString("No");
            }
            collectionTableView.getItems().add(item);
        }
    }

    public void onAddMemberButtonClick() throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("memberDialog.fxml"));
            MemberDialog memberDialog = new MemberDialog(db, null, this);
            fxmlLoader.setController(memberDialog);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add member");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onEditMemberClick() throws IOException{
        try{
            if(membersTableView.getSelectionModel().getSelectedItem() != null){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("memberDialog.fxml"));
                MemberDialog memberDialog = new MemberDialog(db, membersTableView.getSelectionModel().getSelectedItem(), this);
                fxmlLoader.setController(memberDialog);
                Scene scene = new Scene(fxmlLoader.load());

                Stage dialog = new Stage();
                dialog.setScene(scene);
                dialog.setTitle("Edit member");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.showAndWait();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onDeleteMemberClick(){
        try{
            if(membersTableView.getSelectionModel().getSelectedItem() != null){
                db.deleteMember(membersTableView.getSelectionModel().getSelectedItem());
                loadMembersTableView();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void loadMembersTableView(){
        List<Member> members = db.getAllMembers();

        membersTableView.getItems().clear();

        memberIdentifierColumn.setCellValueFactory(new PropertyValueFactory("memberId"));
        memberFirstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        memberLastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        memberBirthDateColumn.setCellValueFactory(new PropertyValueFactory("birthdate"));

        for (Member member : members) {
            membersTableView.getItems().add(member);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("");
        lendErrorLabel.setText("");
        receiveErrorLabel.setText("");
        lendStatusLabel.setText("");
        receiveStatusLabel.setText("");
    }
}