package com.scibag.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scibag.connectors.Database;

public class CourseModel extends Model{

	public CourseModel(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	
	//consider using their holders instead of the hashmap later
	public ArrayList<HashMap<String,String>> getCourses(){
		
		ArrayList<HashMap<String,String>> courses = new ArrayList<>();
		
		
		
		try {
			ResultSet courseResult = db.getStatement().executeQuery("SELECT * FROM Courses");
			
			return resultSetToArrayList(courseResult);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public ArrayList<Integer> getIDsOfCode(ArrayList<String> courseCodes){
		
		ArrayList<Integer> codeIDs = new ArrayList<>();
		
		ArrayList<String> quotedCodes = new ArrayList<>();
		courseCodes.forEach(courseCode->{
			quotedCodes.add("'"+courseCode+"'");
		});
		
		String codes = String.join(",", quotedCodes);
		//TODO:check the courseIDs 4 sql injection;
		String query = "SELECT courseID FROM Courses WHERE shortCode IN ("+codes+")";
		System.out.println(query);
		
		try {
			ResultSet courseResult = db.getStatement().executeQuery(query);
			while(courseResult.next()){
				codeIDs.add(courseResult.getInt("courseID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codeIDs;
	}
	
	
}
