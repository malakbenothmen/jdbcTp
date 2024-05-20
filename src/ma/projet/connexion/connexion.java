package ma.projet.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class connexion {
	  private static final String URL = "jdbc:mysql://localhost/jdbc";
	  private static String login = "root";
	  private static String password = "";
	       
	    public static Connection getCn() {
	    	Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection( URL ,login,password);
	            System.out.println("Connexion établie avec succès !");
	        } catch (ClassNotFoundException e) {
	            System.err.println("Erreur de chargement du driver MySQL : " + e.getMessage());
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
	        }
	        return connection;
	    }

} 
