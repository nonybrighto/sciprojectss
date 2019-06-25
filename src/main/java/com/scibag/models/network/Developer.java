package com.scibag.models.network;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import com.scibag.connectors.Database;

public class Developer extends User{

	public Developer(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean addDeveloper(Long userID, String url){
		
		
		String query = "INSERT INTO Developers(developerID, url) " + 
				" VALUES (?, ?)";
		
		
		try {
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setLong(1, userID);
			preparedStatement.setString(2, url);
						
			preparedStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean urlTaken(String url){
		
		
		try {
			String query = "SELECT 1 FROM Developers WHERE url = ? LIMIT 1";
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, url);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			if(pluginResult.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	

}
