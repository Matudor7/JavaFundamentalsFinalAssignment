package nl.inholland.nl.tudornosca678549libraryassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import nl.inholland.nl.tudornosca678549libraryassignment.controller.Login;
import nl.inholland.nl.tudornosca678549libraryassignment.data.Database;
import nl.inholland.nl.tudornosca678549libraryassignment.model.Item;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("Closing window...");
            }
        });
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
    }
}