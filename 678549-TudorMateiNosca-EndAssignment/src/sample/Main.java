package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    System.out.println("Closing window...");
                    Database db = new Database();

                    List<Item> items = db.GetAllItems();
                    List<Member> members = db.GetAllMembers();

                    for(Item item : items){
                        System.out.println(item.getItemCode() + ". " + item.getTitle());
                    }

                    db.CloseStreams();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);

        Database db = new Database();

        List<Item> items = db.GetAllItems();

        for(Item item : items){
            System.out.println(item.getItemCode() + ". " + item.getTitle());
        }
    }
}
