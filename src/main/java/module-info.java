module com.mycompany.projectjava2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.projectjava2 to javafx.fxml;
    exports com.mycompany.projectjava2;
}
