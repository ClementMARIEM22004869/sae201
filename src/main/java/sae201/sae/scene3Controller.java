package sae201.sae;

import javafx.beans.binding.Binding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.beans.binding.Binding.*;

public class scene3Controller {
    @FXML
    private Button fenetre0;

    @FXML
    private Button refresh;

    @FXML
    private PieChart camembert2;

    @FXML
    public void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Catégorie 1", 30),
                new PieChart.Data("Catégorie 2", 20),
                new PieChart.Data("Catégorie 3", 50)
        );
        camembert2.setData(pieChartData);

    }


    @FXML
    public void fenetre0c(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshc (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphtt.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
