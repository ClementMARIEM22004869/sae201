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

    @FXML
    public void lireDonees() {
        String csvFile = "src/main/resources/sae201/sae/donne.csv";
        String line;
        List<String[]> lignes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "");
                String[] valeurs = line.split(",");
                lignes.add(valeurs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int compteur = 0;
        for (String[] ligne : lignes) {
            for (String valeur : ligne) {
                //System.out.print(valeur + " ");
                compteur +=1;
            }

        }
        System.out.println(compteur + " séismes");
    }
}
