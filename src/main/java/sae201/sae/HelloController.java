package sae201.sae;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class HelloController {
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
    private TextField date2;
    @FXML
    private ComboBox selecteurLoc;
    @FXML
    private TextField nom;
    @FXML
    private TextField intensite;
    @FXML
    private Button fenetre1;
    @FXML
    private Button fenetre2;
    @FXML
    private Button refresh;
    private List<String[]> resultats = new ArrayList<String[]>();

    /**
     * Initialise l'application.
     * Lit les données à partir d'un fichier CSV et remplit le sélecteur avec les localisations.
     */
    public void initialize(){
        lireDonnees();
        mettreDansSelecteurLoc();
    }
    /**
     * lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans
     */
    @FXML
    public void lireDonnees() {
        String csvFile = "src/main/resources/sae201/sae/donnee.csv";
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Afficher les données dans les colones correspondantes
     * @param resultat La liste de tableaux de chaînes de caractères contenant les données à afficher.
     */
    @FXML
    public void affichDonnee(List<String[]> resultat) {
        tableView.getItems().clear();//on clear l'ancienne entrée.
        identifiantColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        heureColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        regionEpicentraleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
        chocColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));
        X.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6]));
        Y.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[7]));
        latitude.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[8]));
        longitude.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[9]));
        intensiteColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[10]));
        //on ajoute les données dans le TableView
        tableView.getItems().addAll(resultat);
    }
    /**
     * Recherche et filtre les données en fonction de l'entrée de l'utilisateur.
     * @return La liste filtrée de tableaux de chaînes de caractères.
     */
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
            //si la date est du bon format
            try {
                dateValeur = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // si la date est du mauvais format
            } catch (DateTimeParseException e) {
                System.out.println("Erreur de format de date : " + dateString);
            }

            // Vérifier si la date est comprise entre dateSelectionnee et date2Selectionnee
            if (dateSelectionnee != null && date2Selectionnee != null && dateValeur!= null) {
                LocalDate parsedDateSelectionnee = null;
                LocalDate parsedDate2Selectionnee = null;
                //si la date selectionnee par l'utilisateur est bonne
                try {
                    parsedDateSelectionnee = LocalDate.parse(dateSelectionnee);
                    parsedDate2Selectionnee = LocalDate.parse(date2Selectionnee);
                    //si la date selectionnee par l'utilisateur n'est pas bonne
                } catch (DateTimeParseException ex) {
                    System.out.println("Erreur de format de date sélectionnée");
                    continue;
                }
                //on récupère toutes les valeurs entre les deux dates selectionné
                if (dateValeur.isAfter(parsedDate2Selectionnee) && dateValeur.isBefore(parsedDateSelectionnee)) {
                    if (estCompatible(valeurs, localisationSelectionnee, intensiteSelectionnee)) {
                        resultats.add(valeurs);
                    }
                }
            }
            //si aucune date n'est selectionné
            if (dateSelectionnee == null && date2Selectionnee == null) {
                if (estCompatible(valeurs, localisationSelectionnee, intensiteSelectionnee)) {
                    resultats.add(valeurs);
                }
            }
        }

        affichDonnee(resultats);
        return resultats;

    }
    /**
     * Remplit le sélecteur avec les localisations uniques à partir des données dans le ComboBox.
     */
    public void mettreDansSelecteurLoc(){
        for (String[] dns : donnees){
            if (!selecteurLoc.getItems().toString().contains(dns[4])){
                selecteurLoc.getItems().add(dns[4]);
            }
        }
    }
    /**
     * Vérifie si les valeurs sont compatibles avec l'entrée de l'utilisateur.
     * @param valeurs Le tableau de valeurs à vérifier.
     * @param localisation La localisation sélectionnée.
     * @param intensite L'intensité sélectionnée.
     * @return True si les valeurs sont compatibles, false sinon.
     */
    private boolean estCompatible(String[] valeurs, String localisation, String intensite) {
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
     * Bascule vers la fenêtre 1.
     * @param event L'événement qui a déclenché l'action.
     */
    @FXML
    public void fenetre1c (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) fenetre1.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Bascule vers la fenêtre 2.
     * @param event L'événement qui a déclenché l'action.
     */
    @FXML
    public void fenetre2c (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphtt.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) fenetre2.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Actualise la vue.
     * @param event L'événement qui a déclenché l'action.
     */
    @FXML
    public void refreshc (ActionEvent event) {
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

}