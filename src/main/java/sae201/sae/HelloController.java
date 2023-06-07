package sae201.sae;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


import java.util.ArrayList;
import java.util.Locale;

import static sae201.sae.HelloApplication.initRootLayout;

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
    private DatePicker date;
    @FXML
    private TextField localisation;
    @FXML
    private TextField nom;
    @FXML
    private TextField intensite;
    @FXML
    //lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans.
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
//afficher les données dans les colones correspondantes
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

    //fonction pour rechercher et filtrer
    public List<String[]> rechercher() {
        lireDonnees();
        List<String[]> resultats = new ArrayList<>();
        resultats.clear();//on clear l'ancien tableau au où
        // Récupérer les valeurs saisies par l'utilisateur (prend en compte la casse)
        String dateSelectionnee = (date.getValue() != null) ? date.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) : null;
        String localisationSelectionnee = localisation.getText().toUpperCase();
        String nomSelectionnee = nom.getText().toUpperCase();
        String intensiteSelectionnee = intensite.getText().toUpperCase();
        //parcours des données et récupération des bonnes
        for (String[] valeurs : donnees) {
            if (estCompatible(valeurs, dateSelectionnee, localisationSelectionnee, nomSelectionnee, intensiteSelectionnee)) {
                resultats.add(valeurs);
            }
        }
        affichDonnee(resultats);
        return resultats;
    }
    public void stats() {
        lireDonnees();
        int compteur = 0;
        double max = 0;
        double min = 5;
        String region = localisation.getText().toUpperCase();;
        for (String[] ligne : donnees) {
            if (ligne[4].contains(region)) {
                String magnitudeString = ligne[10].trim(); // Supprimer les espaces en début et fin de la chaîne
                if (!magnitudeString.isEmpty()) {
                    double currentMagnitude = Double.parseDouble(magnitudeString);
                    if (currentMagnitude > max) {
                        max = currentMagnitude;
                    }
                }
                String minS = ligne[10].trim(); // Supprimer les espaces en début et fin de la chaîne
                if (!minS.isEmpty()) {
                    double currentMin = Double.parseDouble(minS);
                    if (currentMin < min) {
                        min = currentMin;
                    }
                }
                compteur += 1;
            }
        }
        System.out.println("Nombre de séismes en "+region+" : " + compteur);
        System.out.println("Le plus gros séisme en "+ region + " est de magnitude : " + max);
        System.out.println("Le plus petit séisme en "+region+" est de magnitude : " + min);

    }

//fonction pour vérifier si les valeurs sont compatibles avec les entrées utilisateur
//on vérifie pour chaque entrée si la valeur est compatible si une des valeurs n'est pas compatible on renvoie false.
    private boolean estCompatible(String[] valeurs, String dateSelectionnee, String localisation, String nom, String intensite) {
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
        // Vérifier la compatibilité avec le nom
        if (nom != null && !nom.isEmpty()) {
            String valeurNom = valeurs[3];
            if (!valeurNom.contains(nom)) {
                return false; // L'entrée n'est pas compatible avec le nom
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


    @FXML
    public void vga () {
        lireDonnees();
        int dénominateur = 0;
        double numérateur = 0;

        for (String[] ligne : donnees) {
            if (ligne.length > 10) {
                String magnitudeString = ligne[10];
                if (!magnitudeString.isEmpty()) {
                    try {
                        double magnitude = Double.parseDouble(magnitudeString);
                        System.out.println("Magnitude du séisme : " + magnitude);
                        numérateur += magnitude;
                    } catch (NumberFormatException e) {
                        System.out.println("La magnitude du séisme n'est pas un nombre valide : " + magnitudeString);
                    }
                } else {
                    System.out.println("La magnitude du séisme est une chaîne vide.");
                }
            } else {
                System.out.println("La ligne ne contient pas suffisamment d'éléments pour la magnitude.");
            }

            dénominateur++;
        }

        double moyenne = numérateur / dénominateur;
        System.out.println("Moyenne sur l'échelle Richter : " + moyenne);
    }

    public void fenetre0(ActionEvent actionEvent) {initRootLayout();}
    public void fenetre1(ActionEvent actionEvent) {initRootLayout();}
    public void fenetre2(ActionEvent actionEvent) {initRootLayout();}
    public void refresh(ActionEvent actionEvent) {initRootLayout();}
}
