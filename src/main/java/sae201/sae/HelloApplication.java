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
    public Button refresh;
    @FXML
    public Button fenetre0;
    @FXML
    public Button fenetre1;
    @FXML
    public Button fenetre2;
    private Stage primaryStage;

    private FXMLLoader loader;
    /*
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("graph.fxml"));
        //Image image = new Image(getClass().getResourceAsStream("Univ_Aix-Marseille_-_IUT.png"));
        //image2.setImage(image);
        Scene scene = new Scene(fxmlLoader.load(), 1000, 900);
        stage.setTitle("Seisme");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

     */

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Seisme");

        initRootLayout();
    }

    private void initRootLayout() {
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
                loader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));
            });

            fenetre0.setOnAction(event -> {
                refreshScene();
                loader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));
            });

            fenetre1.setOnAction(event -> {
                refreshScene();
                loader.setLocation(HelloApplication.class.getResource("graph.fxml"));
            });

            fenetre2.setOnAction(event -> {
                refreshScene();
                loader.setLocation(HelloApplication.class.getResource("hello-view.fxml"));
            });

            // Affiche la scène contenant le layout principal
            Scene scene = new Scene(loader.load(), 1000, 900);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshScene() {
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
