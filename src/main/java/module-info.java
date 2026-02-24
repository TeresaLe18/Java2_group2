module com.mycompany.projectjava2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires java.net.http;

    opens com.mycompany.projectjava2 to javafx.fxml;
    opens com.mycompany.projectjava2.controller to javafx.fxml;
    opens com.mycompany.projectjava2.model to javafx.base;

    exports com.mycompany.projectjava2;
}
