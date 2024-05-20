package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	
	public void start(Stage primaryStage) {
	    try {
	        // Charger le fichier FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Etudiant.fxml"));
	        Parent root = loader.load();

	        // Créer une nouvelle scène avec le contenu chargé du fichier FXML
	        Scene scene = new Scene(root);

	        // Appliquer la feuille de style CSS
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	        // Définir la scène sur la fenêtre principale
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("gestion etudiant"); // Vous pouvez définir le titre de votre fenêtre ici
	        primaryStage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		launch(args);
	}
}
