package sae201.sae;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;



    public void ouvrirFichierCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("../../ressources/sae201.sae/donne.csv", "*.csv"));

        File fichierCSV = fileChooser.showOpenDialog(new Stage());

        if (fichierCSV != null) {
            lireFichierCSV(fichierCSV);
        }
    }public void lireFichierCSV(File fichierCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] donnees = ligne.split(","); // Modifier le séparateur si nécessaire
                // Traitez les données du fichier CSV ici
                // Exemple : Affichage des données dans la console
                for (String donnee : donnees) {
                    System.out.print(donnee + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


}
