package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class MainWindow {
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
    TableView collectionTableView;

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
    TableView membersTableView;

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

    public void DisplayWelcomeMessage(String name){
        welcomeLabel.setText("Welcome, " + name);
    }

    public void OnLendingReceivingButtonClick(){
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

    public void OnMembersButtonClick() throws IOException, ClassNotFoundException{
        lendingReceivingPane.setVisible(false);
        membersPane.setVisible(true);
        collectionPane.setVisible(false);

        LoadMembersTableView();
    }

    public void LoadCollectionTableView() throws IOException, ClassNotFoundException {
        db = new Database();
        List<Item> items = db.GetAllItems();

        collectionTableView.getItems().clear();

        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("availabilityString"));
        itemTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        for(Item item : items){
            if(item.getIsAvailable()){
                item.setAvailabilityString("Yes");
            }else{
                item.setAvailabilityString("No");
            }
            collectionTableView.getItems().add(item);
        }
    }

    public void LoadMembersTableView()throws IOException, ClassNotFoundException{
        db = new Database();
        List<Member> members = db.GetAllMembers();

        membersTableView.getItems().clear();

        memberIdentifierColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        memberFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        memberLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        memberBirthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        for(Member member : members){
            membersTableView.getItems().add(member);
        }
    }
}
