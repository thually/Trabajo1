module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;

    opens trabajo2 to javafx.fxml;
    exports trabajo2;
}