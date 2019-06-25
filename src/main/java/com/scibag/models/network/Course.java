package com.scibag.models.network;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.scibag.connectors.Database;
import com.scibag.models.Model;

public class Course extends Model{

	Long courseID;
	public Course(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	public Course(Database db, Long courseID) {
		
		super(db);
		this.courseID = courseID;
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public long getCourseID(String shortCode){
		
		
		try {
			String query = "SELECT courseID FROM Courses WHERE shortCode = ? LIMIT 1";
			
			System.out.println(query);
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, shortCode);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			while(pluginResult.next()){
				
				return pluginResult.getLong("courseID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0l;
		
	}
	
	
	
	
	
	

}
