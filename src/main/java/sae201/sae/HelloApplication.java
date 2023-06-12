package sae201.sae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

    public class HelloApplication extends Application {
        /**
         * Cette méthode est appelée lors du démarrage de l'application JavaFX.
         * Elle configure la fenêtre principale de l'application et affiche le contenu initial.
         * @param primaryStage Le stage principal de l'application.
         * @throws IOException En cas d'erreur lors du chargement du fichier FXML.
         */
        @Override
        public void start(Stage primaryStage) throws IOException {
            // Configuration des propriétés système pour l'environnement JavaFX
            System.setProperty("javafx.platform", "desktop");
            System.setProperty("http.agent", "Gluon Mobile/1.0.3");

            // Chargement de la vue principale depuis le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            // Création de la scène avec la vue principale
            Scene scene = new Scene(root);

            // Configuration de la fenêtre principale
            primaryStage.setTitle("Seisme");
            primaryStage.setScene(scene);
            primaryStage.setWidth(1000);
            primaryStage.setHeight(950);

            // Affichage de la fenêtre principale
            primaryStage.show();
        }

        /**
         * Point d'entrée de l'application.
         * Cette méthode lance l'application JavaFX en appelant la méthode launch().
         * @param args Les arguments de ligne de commande.
         */
        public static void main(String[] args) {
            // Lancement de l'application JavaFX
            launch();
        }
}
