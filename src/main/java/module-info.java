module com.pt2021_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.pt2021_1 to javafx.fxml;
    exports com.pt2021_1;
}