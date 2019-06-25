package com.scibag.models.network;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.Statement;
import com.scibag.connectors.Database;
import com.scibag.models.Model;

public class User extends Model{

	Long userID;
	
	public User(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	public User(Database db , Long userID) {
		super(db);
		this.userID = userID;
		// TODO Auto-generated constructor stub
	}
	
	
	public long addUser(String username, String email , String password){
		
		
		String query = "INSERT INTO Users(username, email, password) " + 
				" VALUES (?, ?, ?)";
		
		
		try {
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
						
			preparedStatement.executeUpdate();
			
			 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	 return generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("failed.");
		            }
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0l;
	}
	
	
	public boolean canLogin(String username, String password){
		
		
		try {
			String query = "SELECT 1 FROM Users WHERE username = ? AND password = ? LIMIT 1";
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
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
	
	public String getUsername(){
		
		try {
			String query = "SELECT username FROM Users WHERE userID = "+this.userID+" LIMIT 1";
			
			System.out.println(query);
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			while(pluginResult.next()){
				
				return pluginResult.getString("username");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Long getUserID(String username){
		
		try {
			String query = "SELECT userID FROM Users WHERE username = ? LIMIT 1";
			
			System.out.println(query);
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			while(pluginResult.next()){
				
				return pluginResult.getLong("userID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean usernameTaken(String username){
		
		
		try {
			String query = "SELECT 1 FROM Users WHERE username = ? LIMIT 1";
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, username);
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
	
	public boolean emailTaken(String username){
		
		
		try {
			String query = "SELECT 1 FROM Users WHERE email = ? LIMIT 1";
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, username);
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
