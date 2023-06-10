package sae201.sae;

import com.gluonhq.maps.MapLayer;
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
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapLayer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static javafx.beans.binding.Binding.*;

public class scene3Controller {
    @FXML
    private Label welcomeText;
    @FXML
    private Button btn;

    @FXML
    //le tableau de String
    private List<String[]> donnees = new ArrayList<String[]>();
    @FXML
    private TableView<String[]> tableView;
    @FXML
    private TableColumn<String[], String> identifiantColumn;
    @FXML
    private TableColumn<String[], String> dateColumn;
    @FXML
    private TableColumn<String[], String> heureColumn;
    @FXML
    private TableColumn<String[], String> intensiteColumn;
    @FXML
    private TableColumn<String[], String> qualiteIntensiteColumn;
    @FXML
    private TableColumn<String[], String> nomColumn;
    @FXML
    private TableColumn<String[], String> regionEpicentraleColumn;
    @FXML
    private TableColumn<String[], String> chocColumn;
    @FXML
    private TableColumn<String[], String> X;
    @FXML
    private TableColumn<String[], String> latitude;
    @FXML
    private TableColumn<String[], String> Y;
    @FXML
    private TableColumn<String[], String> longitude;
    @FXML
    private DatePicker date;
    @FXML
    private TextField localisation;
    @FXML
    private TextField nom;
    @FXML
    private TextField intensite;
    private List<String[]> resultats = new ArrayList<String[]>();
    @FXML
    private Button fenetre0;
    HelloController helloController = new HelloController();
    @FXML
    private Button refresh;
    @FXML
    private StackPane stackpane;
    @FXML
    private ComboBox selecteurLoc;
    private MapLayer mapLayer;
    private MapView map;
    public void initialize(){
        lireDonnees();
        mettreDansSelecteurLoc();
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        map = new MapView();
        map.setZoom(5);
        MapPoint mapPoint = new MapPoint(46.227638, 2.213749);
        map.flyTo(0, mapPoint, 0.1);
        map.setMinSize(stackpane.getWidth(), stackpane.getHeight());
        map.setMaxSize(stackpane.getMaxWidth(), stackpane.getMaxHeight());
        stackpane.getChildren().add(map);
    }
    public void mettreDansSelecteurLoc(){
        for (String[] dns : donnees){
            if (!selecteurLoc.getItems().toString().contains(dns[4])){
                selecteurLoc.getItems().add(dns[4]);
            }
        }
    }
    public void lireDonnees() {
        String csvFile = "src/main/resources/sae201/sae/donne.csv";
        //ligne actuelle
        String line;
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Ignorer la première ligne (en têtes des colonnes)
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                //on enlève les ""
                line = line.replaceAll("\"", "");
                //on met dans le tableau de String valeurs les valeurs
                String[] valeurs = line.split(",");
                if (valeurs.length < 11) {
                    valeurs = Arrays.copyOf(valeurs, 11);
                    valeurs[10] = " ";
                }

                //on ajoute les valeurs dans le tableau de String
                donnees.add(valeurs);

                for (String v : valeurs) {
                    System.out.println(v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public List<String[]> rechercher() {
        lireDonnees();
        resultats.clear();//on clear l'ancien tableau
        // Récupérer les valeurs saisies par l'utilisateur (prend en compte la casse)
        String dateSelectionnee = (date.getValue() != null) ? date.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) : null;
        String localisationSelectionnee = (selecteurLoc.getValue() != null) ? selecteurLoc.getValue().toString() : null;
        String intensiteSelectionnee = intensite.getText().toUpperCase();
        //parcours des données et récupération des bonnes
        for (String[] valeurs : donnees) {
            if (estCompatible(valeurs, dateSelectionnee, localisationSelectionnee, intensiteSelectionnee)) {
                resultats.add(valeurs);
                this.resultats.add(valeurs);
            }
        }
        affichPointCarte();
        return resultats;

    }
    private boolean estCompatible(String[] valeurs, String dateSelectionnee, String localisation, String intensite) {
        // Vérifier la compatibilité avec la date sélectionnée
        if (dateSelectionnee != null && !dateSelectionnee.isEmpty()) {
            String valeurDate = valeurs[1];
            if (!valeurDate.contains(dateSelectionnee)) {
                return false; // L'entrée n'est pas compatible avec la date sélectionnée
            }
        }
        // Vérifier la compatibilité avec la localisation
        if (localisation != null && !localisation.isEmpty()) {
            String valeurLocalisation = valeurs[4];
            if (!valeurLocalisation.contains(localisation)) {
                return false; // L'entrée n'est pas compatible avec la localisation
            }
        }
        if (intensite != null && !intensite.isEmpty()) {
            String valeurIntensite = valeurs[10];
            if (!valeurIntensite.contains(intensite)) {
                return false; // L'entrée n'est pas compatible avec le nom
            }
        }
        return true; // L'entrée est compatible avec toutes les valeurs saisies par l'utilisateur
    }

    public void affichPointCarte(){
        map.removeLayer(mapLayer);
        for (String[] rslt : resultats){
            if (rslt[6]!= ""){
                double x = Double.parseDouble(rslt[8]);
                double y = Double.parseDouble(rslt[9]);
                MapPoint pointMap = new MapPoint(x, y);
                CustomCircleMarkerLayer customCircleMarkerLayer = new CustomCircleMarkerLayer(pointMap);
                map.addLayer(customCircleMarkerLayer);
            }
        }
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
