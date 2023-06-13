package sae201.sae;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class scene2Controller {
    @FXML
    private Button fenetre0;
    @FXML
    private Button fenetre2;
    private List<String[]> donnees = new ArrayList<String[]>();
    @FXML
    private Button refresh;
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
    private List<String[]> resultats = new ArrayList<String[]>();
    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private Label nbseismes;
    @FXML
    private Label intbasse;
    @FXML
    private Label inthaute;
    @FXML
    private Label intmoy;
    @FXML
    private HBox hboxGr;
    @FXML
    private PieChart pieChart;
    public void initialize(){
        lireDonnees();
        mettreDansSelecteurLoc();

    }
    public void pieChart(){
// compter le nombre d'occurrences de chaque villes
        Map<String, Integer> comptage = new HashMap<>();
        for (String[] resultat : resultats) {
            String ville = resultat[4];
            comptage.put(ville, comptage.getOrDefault(ville, 0) + 1);
        }

// créer les objets PieChart.Data à partir des résultats
        ObservableList<PieChart.Data> donnees = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : comptage.entrySet()) {
            PieChart.Data section = new PieChart.Data(entry.getKey(), entry.getValue());
            donnees.add(section);
        }

// créer le PieChart et ajouter les données
        PieChart pieChart = new PieChart(donnees);
        pieChart.setTitle("Répartition du nombre de séismes par ville");
        pieChart.setPrefSize(800, 800);
        hboxGr.getChildren().add(pieChart);
    }
    @FXML
    public void graph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Intensité");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de séismes");
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            List<Double> temp = new ArrayList<>();
            for (String[] rslt : resultats){
                temp.add(Double.parseDouble(rslt[10]));
            }
            //une map pour ajouter les intensités ainsi que le nombre d'occurence de chaque intensité
            Map<Double, Integer> intensiteFrequences = new HashMap<>();

            // Compter les fréquences des intensités
            for (Double intensite : temp) {
                intensiteFrequences.put(intensite, intensiteFrequences.getOrDefault(intensite, 0) + 1);
            }

            // Ajouter les données au BarChart
            for (Map.Entry<Double, Integer> entry : intensiteFrequences.entrySet()) {
                Double intensite = entry.getKey();
                Integer frequence = entry.getValue();
                series.getData().add(new XYChart.Data<>(String.valueOf(intensite), frequence));
            }

            // Ajouter la série de données au Barchart
        barChart.getData().add(series);
            hboxGr.getChildren().add(barChart);
        }
    @FXML
    //lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans
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

    //fonction pour rechercher et filtrer
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
        stats();
        moyenne();
        graph();
        pieChart();
        return resultats;

    }
    //Mettre les localisation dans le ComboBox
    public void mettreDansSelecteurLoc(){
        for (String[] dns : donnees){
            if (!selecteurLoc.getItems().toString().contains(dns[4])){
                selecteurLoc.getItems().add(dns[4]);
            }
        }
    }
    //fonction pour vérifier si les valeurs sont compatibles avec les entrées utilisateur
    //on vérifie pour chaque entrée si la valeur est compatible si une des valeurs n'est pas compatible on renvoie false.
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

    //Statistiques
    public void stats(){
        double maxMagnitude = Double.MIN_VALUE;
        double minMagnitude = Double.MAX_VALUE;
        int nbSeisme = resultats.size();
        nbseismes.setText(String.valueOf(nbSeisme));
        for ( String[] rslt: resultats){
            String magnitudeString = rslt[10].trim();
            if (!magnitudeString.isEmpty()) {
                double currentMagnitude = Double.parseDouble(magnitudeString);
                if (currentMagnitude > maxMagnitude) {
                    maxMagnitude = currentMagnitude;
                }
                if (currentMagnitude < minMagnitude) {
                    minMagnitude = currentMagnitude;
                }
            }
        }
        intbasse.setText(String.valueOf(minMagnitude));
        inthaute.setText(String.valueOf(maxMagnitude));
    }

    //calculer la moyenne
    @FXML
    public void moyenne() {
        lireDonnees();
        int dénominateur = 0;
        double numérateur = 0;

        for (String[] ligne : resultats) {
            if (ligne.length > 10) {
                String magnitudeString = ligne[10];
                if (!magnitudeString.isEmpty()) {
                    try {
                        double magnitude = Double.parseDouble(magnitudeString);
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
        intmoy.setText(String.valueOf(moyenne));
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