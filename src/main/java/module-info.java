module com.deadlywords {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.deadlywords to javafx.fxml;
    exports com.deadlywords;
}
