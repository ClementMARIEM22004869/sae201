package sae201.sae;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private ImageView image1;
    @FXML
    public ImageView image2;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Image image = new Image(getClass().getResourceAsStream("Univ_Aix-Marseille_-_IUT.png"));
        //image2.setImage(image);
        Scene scene = new Scene(fxmlLoader.load(), 1700, 920);
        stage.setTitle("Seisme");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
