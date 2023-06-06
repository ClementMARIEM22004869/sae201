package sae201.sae;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


import java.util.ArrayList;

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
    //lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans.
    public void lireDonees() {
        String csvFile = "src/main/resources/sae201/sae/donne.csv";
        //ligne actuelle
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                //on enlève les ""
                line = line.replaceAll("\"", "");
                //on met dans le tableau de String valeurs les valeurs
                String[] valeurs = line.split(",");
                //on ajoute les valeurs dans le tableau de String
                donnees.add(valeurs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
@FXML
    public void afficherDonnees() {
        lireDonees();

        identifiantColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        heureColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        intensiteColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        qualiteIntensiteColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));
        regionEpicentraleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6]));
        chocColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[7]));
        // Utilisez les autres méthodes setCellValueFactory pour les autres colonnes

        tableView.getItems().addAll(donnees);
    }


    public void stats(){
        int compteur = 0;
        for (String[] ligne : donnees) {
            for (String valeur : ligne) {
                //System.out.print(valeur + " ");
                compteur +=1;
            }

        }
        System.out.println(compteur + " séismes");
    }
}
