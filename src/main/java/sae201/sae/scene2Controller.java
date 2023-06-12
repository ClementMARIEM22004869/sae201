package sae201.sae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class scene2Controller {
    @FXML
    private Button fenetre0;
    @FXML
    private Button fenetre2;

    @FXML
    private Button refresh;

    @FXML
    private PieChart camembert;

    @FXML
    public void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Catégorie 1", 30),
                new PieChart.Data("Catégorie 2", 20),
                new PieChart.Data("Catégorie 3", 50)
        );
        camembert.setData(pieChartData);

    }

    /**
     * Action exécutée lors du clic sur le bouton fenetre0.
     * Charge le fichier FXML "hello-view.fxml" et affiche la scène correspondante.
     *
     * @param event L'événement de clic sur le bouton.
     */
    @FXML
    public void fenetre0c(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) fenetre0.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Action exécutée lors du clic sur le bouton fenetre2.
     * Charge le fichier FXML "graphtt.fxml" et affiche la scène correspondante.
     *
     * @param event L'événement de clic sur le bouton.
     */
    @FXML
    public void fenetre2c(ActionEvent event) {
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
    /**
     * Action exécutée lors du clic sur le bouton refresh.
     * Charge le fichier FXML "graph.fxml" et affiche la scène correspondante.
     *
     * @param event L'événement de clic sur le bouton.
     */
    @FXML
    public void refreshc (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}