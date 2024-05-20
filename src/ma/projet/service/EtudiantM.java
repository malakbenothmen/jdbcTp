package ma.projet.service;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import application.etudiant;
import ma.projet.connexion.connexion;

public class EtudiantM {

	
	public boolean create(etudiant o) {
   
	    try {
	        Connection con = connexion.getCn();
	        if (con == null || con.isClosed()) {
	            System.out.println("Connection is closed or null.");
	            return false;
	        }
	        String sql = "INSERT INTO etudiant (nom, prenom, sexe, filiere) VALUES (?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, o.getNom());
	        statement.setString(2, o.getPrenom());
	        statement.setString(3, o.getSexe());
	        statement.setString(4, o.getFiliere());
	        int rowsInserted = statement.executeUpdate();
	        return rowsInserted > 0;
	      } catch (SQLException ex) {
	        ex.printStackTrace();
	        return false;
	    } 
	    
	}


	
	
	public boolean delete (etudiant o)
	{
        try {
            Connection con = connexion.getCn();
            String sql = "DELETE FROM etudiant WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, o.getId());
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            con.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		
	}
	
	public boolean update (etudiant o)
	{
        try {
            Connection conn = connexion.getCn();
            String sql = "UPDATE etudiant SET nom = ?, prenom = ? , sexe = ? , filiere= ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, o.getNom());
            stmt.setString(2, o.getPrenom());
            stmt.setString(3, o.getSexe());
            stmt.setString(4, o.getFiliere());
            stmt.setInt(5, o.getId());
            int rowsUpdated = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	
	
	public etudiant findById (int id)
	{
		etudiant o = null;
		 try {
	            Connection con = connexion.getCn();
	            String sql = "SELECT * FROM etudiant where id = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setInt(1, id);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	               o = new etudiant(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"),resultSet.getString("sexe"),resultSet.getString("filiere"));
	            }
	            resultSet.close();
	            statement.close();
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return o;
		
		
	}
	
	
	
	
	
	public List < etudiant > findAll( )
	{
		  List<etudiant> listEtud = new ArrayList<>();
	        try {
	            Connection con = connexion.getCn();
	            String sql = "SELECT * FROM etudiant";
	            PreparedStatement statement = con.prepareStatement(sql);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                listEtud.add(new etudiant(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"),resultSet.getString("sexe"),resultSet.getString("filiere")));
	            }
	            resultSet.close();
	            statement.close();
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return listEtud;
	}
}
