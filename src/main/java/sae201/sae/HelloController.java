package sae201.sae;

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
    public void afficherDonnees() {
        lireDonees();

        // Créer les colonnes du TableView
        TableColumn<String[], String> identifiantColumn = new TableColumn<>("Identifiant");
        TableColumn<String[], String> dateColumn = new TableColumn<>("Date (AAAA/MM/JJ)");
        // Ajouter les autres colonnes...

        // Ajouter les colonnes au TableView
        tableView.getColumns().addAll(identifiantColumn, dateColumn, );

        // Ajouter les données au TableView
        for (String[] ligne : donnees) {
            tableView.getItems().add(ligne);
        }
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
