module com.task3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.task3 to javafx.fxml;
    exports com.task3;
}
