module com.example.project_ihm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_ihm to javafx.fxml;
    exports com.example.project_ihm.controllers to javafx.fxml;
    exports com.example.project_ihm;

    opens com.example.project_ihm.controllers to javafx.fxml;
}