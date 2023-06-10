package sae201.sae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;


import java.io.IOException;


    public class HelloApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws IOException {
            System.setProperty("javafx.platform", "desktop");
            System.setProperty("http.agent", "Gluon Mobile/1.0.3");
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Seisme");
            primaryStage.setScene(scene);
            primaryStage.setWidth(1000);
            primaryStage.setHeight(950);
            primaryStage.show();
        }
        public static void main(String[] args) {
            launch();
        }
}
