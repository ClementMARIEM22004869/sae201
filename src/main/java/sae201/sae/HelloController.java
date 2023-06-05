package sae201.sae;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Button btn;
    private BorderPane bdp;
    @FXML
    public void lireDonees(){
        String csvFile = "src/main/resources/sae201/sae/donnee.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                String colonne1 = values[0];
                String colonne2 = values[1];
                System.out.println(line);
                Label donneeLabel = new Label(line);
                bdp.getChildren().add(donneeLabel);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
