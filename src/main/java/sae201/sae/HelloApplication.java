package sae201.sae;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public static Button refresh;
    @FXML
    public static Button fenetre0;
    @FXML
    public static Button fenetre1;
    @FXML
    public static Button fenetre2;
    private static Stage primaryStage;

    private static FXMLLoader loader;

    private static Scene scene;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Seisme");

        initRootLayout();
        primaryStage.show();
    }

    public static void initRootLayout()  {
        try {
            fenetre0 = new Button();
            fenetre1 = new Button();
            fenetre2 = new Button();
            refresh = new Button();

            // Charge le fichier FXML du layout principal
            loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));

            refresh.setOnAction(event -> {
                refreshScene();
            });

            fenetre0.setOnAction(event -> {
                loader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));
            });

            fenetre1.setOnAction(event -> {
                loader.setLocation(HelloApplication.class.getResource("graph.fxml"));
            });

            fenetre2.setOnAction(event -> {
                loader.setLocation(HelloApplication.class.getResource("graphtt.fxml"));
            });

            // Affiche la scène contenant le layout principal
            scene = new Scene(loader.load(), 1000, 900);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void refreshScene() {
        try {
            // Charge le fichier FXML de la nouvelle page à afficher
            BorderPane newPage = loader.load();

            // Met à jour la scène avec la nouvelle page
            Scene scene = new Scene(newPage);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
