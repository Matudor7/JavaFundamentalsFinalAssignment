package nl.inholland.nl.tudornosca678549libraryassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.inholland.nl.tudornosca678549libraryassignment.Main;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Item;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class MainWindow implements Initializable {
    Database db;

    @FXML
    Label welcomeLabel;

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

    public void DisplayWelcomeMessage(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void OnLendingReceivingButtonClick() {
        lendingReceivingPane.setVisible(true);
        membersPane.setVisible(false);
        collectionPane.setVisible(false);
    }

    public void OnCollectionButtonClick() throws IOException, ClassNotFoundException {
        lendingReceivingPane.setVisible(false);
        membersPane.setVisible(false);
        collectionPane.setVisible(true);

        LoadCollectionTableView();
    }

    public void OnMembersButtonClick() throws IOException, ClassNotFoundException {
        lendingReceivingPane.setVisible(false);
        membersPane.setVisible(true);
        collectionPane.setVisible(false);

        loadMembersTableView();
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
                LoadCollectionTableView();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void LoadCollectionTableView() throws IOException, ClassNotFoundException {
        List<Item> items = db.GetAllItems();

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

    public void loadMembersTableView() throws IOException, ClassNotFoundException {
        List<Member> members = db.GetAllMembers();

        membersTableView.getItems().clear();

        memberIdentifierColumn.setCellValueFactory(new PropertyValueFactory("memberId"));
        memberFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        memberLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        memberBirthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        for (Member member : members) {
            membersTableView.getItems().add(member);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("");
    }
}