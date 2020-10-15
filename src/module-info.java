module PicolloClone {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens View;
    opens Controller;
    opens Model;
}