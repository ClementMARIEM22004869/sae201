module sae201.sae {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires com.gluonhq.maps;
    //requires eu.hansolo.tilesfx;

    opens sae201.sae to javafx.fxml;
    exports sae201.sae;
}