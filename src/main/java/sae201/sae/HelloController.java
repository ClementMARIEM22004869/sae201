package sae201.sae;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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
    public void lireDonees(){
        String csvFile = "src/main/resources/sae201/sae/donne.csv";
        String line;
        String cvsSplitBy = ",";
        VBox vbox = new VBox();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                System.out.println(line);
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("\"", "");
                }
                for (int i = 0; i<values.length;i++){
                    Label donneeLabel = new Label(values[i]);
                    vbox.getChildren().add(donneeLabel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bdp.setCenter(vbox);
    }
}
