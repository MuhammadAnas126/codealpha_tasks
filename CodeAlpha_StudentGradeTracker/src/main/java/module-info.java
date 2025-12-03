module com.task1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.task1 to javafx.fxml;
    exports com.task1;
}
