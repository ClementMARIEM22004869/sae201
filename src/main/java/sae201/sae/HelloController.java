package sae201.sae;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private BorderPane bdp;
//lire les données du csv et les ranger dans un tableau de String, chaque valeur est rangé dedans.
    @FXML
    public void lireDonees() {
        String csvFile = "src/main/resources/sae201/sae/donne.csv";
        //ligne actuelle
        String line;
        //le tableau de String
        List<String[]> lignes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                //on enlève les ""
                line = line.replaceAll("\"", "");
                //on met dans le tableau de String valeurs les valeurs
                String[] valeurs = line.split(",");
                //on ajoute les valeurs dans le tableau de String
                lignes.add(valeurs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
