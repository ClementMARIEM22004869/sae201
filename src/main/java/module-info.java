module sae201.sae {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.gluonhq.maps;

    opens sae201.sae to javafx.fxml;
    exports sae201.sae;
}