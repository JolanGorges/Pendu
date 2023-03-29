module com.pendu.pendu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.pendu.pendu to javafx.fxml;
    opens com.pendu.pendu.controllers to javafx.fxml;
    opens com.pendu.pendu.models to javafx.fxml;
    exports com.pendu.pendu;
    exports com.pendu.pendu.controllers;
    exports com.pendu.pendu.models;
}