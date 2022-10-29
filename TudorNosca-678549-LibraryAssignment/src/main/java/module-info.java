module nl.inholland.nl.tudornosca678549libraryassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.nl.tudornosca678549libraryassignment to javafx.fxml;
    exports nl.inholland.nl.tudornosca678549libraryassignment;
    exports nl.inholland.nl.tudornosca678549libraryassignment.controller;
    opens nl.inholland.nl.tudornosca678549libraryassignment.controller to javafx.fxml;

    exports nl.inholland.nl.tudornosca678549libraryassignment.model;
    opens nl.inholland.nl.tudornosca678549libraryassignment.model to javafx.fxml;
}