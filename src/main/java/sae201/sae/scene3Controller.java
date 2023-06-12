package sae201.sae;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private TextField date;
    @FXML
    private TextField localisation;
    @FXML
    private TextField nom;
    @FXML
    private TextField intensite;
    @FXML
    private TextField date2;
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

    /**
     * Initialise la vue.
     * Lit les données, configure le sélecteur de localisation et initialise la carte.
     */
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
    /**
     * Ajoute les localisations uniques dans le sélecteur de localisation.
     */
    public void mettreDansSelecteurLoc(){
        for (String[] dns : donnees){
            if (!selecteurLoc.getItems().toString().contains(dns[4])){
                selecteurLoc.getItems().add(dns[4]);
            }
        }
    }
    /**
     * lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans.
     */
    public void lireDonnees() {
        String csvFile = "src/main/resources/sae201/sae/donnee.csv";
        //ligne actuelle
        String line;
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Ignorer la première ligne (en-têtes des colonnes)
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
    /**
     * Effectue une recherche en fonction des valeurs saisies par l'utilisateur.
     * Les résultats sont stockés dans la liste resultats.
     *
     * @return La liste des résultats de la recherche.
     */
    @FXML
    public List<String[]> rechercher() {
        lireDonnees();
        resultats.clear();//on clear l'ancien tableau
        // Récupérer les valeurs saisies par l'utilisateur (prend en compte la casse)
        String dateSelectionnee = (date.getText() != null && !date.getText().isEmpty()) ? date.getText().replace("/", "-") : null;
        String date2Selectionnee = (date2.getText() != null && !date2.getText().isEmpty()) ? date2.getText().replace("/", "-") : null;


        String localisationSelectionnee = (selecteurLoc.getValue() != null) ? selecteurLoc.getValue().toString() : null;
        String intensiteSelectionnee = intensite.getText().toUpperCase();
        //parcours des données et récupération des bonnes
        for (String[] valeurs : donnees) {
            String dateString = valeurs[1].replace("/","-");
            LocalDate dateValeur = null;
            try {
                dateValeur = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Erreur de format de date : " + dateString);
            }

            // Vérifier si la date est comprise entre dateSelectionnee et date2Selectionnee
            if (dateSelectionnee != null && date2Selectionnee != null && dateValeur!= null) {
                LocalDate parsedDateSelectionnee = null;
                LocalDate parsedDate2Selectionnee = null;
                try {
                    parsedDateSelectionnee = LocalDate.parse(dateSelectionnee);
                    parsedDate2Selectionnee = LocalDate.parse(date2Selectionnee);
                } catch (DateTimeParseException ex) {
                    System.out.println("Erreur de format de date sélectionnée");
                    continue;
                }
                //on récupère toutes les valeurs entre les deux dates selectionné
                if (dateValeur.isAfter(parsedDate2Selectionnee) && dateValeur.isBefore(parsedDateSelectionnee)) {
                    if (estCompatible(valeurs, dateSelectionnee, localisationSelectionnee, intensiteSelectionnee)) {
                        resultats.add(valeurs);
                    }
                }
            }
            //si aucune date n'est selectionné
            if (dateSelectionnee == null && date2Selectionnee == null) {
                if (estCompatible(valeurs, dateSelectionnee, localisationSelectionnee, intensiteSelectionnee)) {
                    resultats.add(valeurs);
                }
            }
        }
        //après avoir toutes les bonnes valeurs, on affiche les points sur la carte
        affichPointCarte();
        return resultats;

    }

    /**
     * Vérifie si les valeurs sont compatibles avec les entrées utilisateur,on vérifie pour chaque entrée si la valeur est compatible si une des valeurs n'est pas compatible on renvoie false
     *
     * @param valeurs              Les valeurs à vérifier.
     * @param dateSelectionnee     La date sélectionnée par l'utilisateur.
     * @param localisation         La localisation sélectionnée par l'utilisateur.
     * @param intensite            L'intensité sélectionnée par l'utilisateur.
     * @return True si les valeurs sont compatibles avec les entrées utilisateur, False sinon.
     */
    private boolean estCompatible(String[] valeurs, String dateSelectionnee , String localisation, String intensite) {
        // Vérifier la compatibilité avec la localisation
        if (localisation != null && !localisation.isEmpty()) {
            String valeurLocalisation = valeurs[4];
            if (!valeurLocalisation.contains(localisation)) {
                return false; // L'entrée n'est pas compatible avec la localisation
            }
        }
        //vérifier la compatibilité avec l'intensité
        if (intensite != null && !intensite.isEmpty()) {
            String valeurIntensite = valeurs[10];
            if (!valeurIntensite.contains(intensite)) {
                return false; // L'entrée n'est pas compatible avec le nom
            }
        }
        return true; // L'entrée est compatible avec toutes les valeurs saisies par l'utilisateur
    }

    /**
     * Affiche les points correspondants aux résultats sur la carte.
     */
    public void affichPointCarte(){
        map.removeLayer(mapLayer);
        for (String[] rslt : resultats){
            //on vérifie si la 6e valeur du tableau n'est pas vide (
            if (rslt[6]!= ""){
                //on récupère le x (stocké dans la 8e valeur du tableau) et le y (stocké dans la 9e valeur du tableau)
                MapPoint pointMap = new MapPoint(Double.parseDouble(rslt[8]), Double.parseDouble(rslt[9]));
                CustomCircleMarkerLayer customCircleMarkerLayer = new CustomCircleMarkerLayer(pointMap);
                map.addLayer(customCircleMarkerLayer);
            }
        }
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Action exécutée lors du clic sur le bouton refresh.
     * Charge le fichier FXML "graphtt.fxml" et affiche la scène correspondante.
     *
     * @param event L'événement de clic sur le bouton.
     */
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
