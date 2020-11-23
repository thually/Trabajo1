module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens trabajo2 to javafx.fxml;
    exports trabajo2;
}