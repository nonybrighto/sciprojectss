package com.scibag.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scibag.connectors.Database;
import com.scibag.connectors.Database_first;

public class BookModel extends Model{

	public BookModel(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	//getRecentlyViewed()
	
	
	public Long addBook(HashMap<String, String> bookDetails){
		
		String query = "INSERT INTO Books(bookName, dateAdded, authors, description, bookCode , level) " + 
				" VALUES (? , CURRENT_TIMESTAMP , ? , ? , ? , ?)";
		
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = db.getConnection().prepareStatement(query);
			
			preparedStatement.setString(1, bookDetails.get("bookName"));
			preparedStatement.setString(2, "scibag");
			preparedStatement.setString(3, bookDetails.get("description"));
			preparedStatement.setString(4, bookDetails.get("bookCode"));
			preparedStatement.setInt(5, Integer.valueOf(bookDetails.get("level")));
			
			preparedStatement.executeUpdate();
			
			 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	 return generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating book failed, no ID obtained.");
		            }
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0l;
	}
	
	public void addBookCourses(Long bookID , ArrayList<Integer> courseIDs){
		
		
		courseIDs.forEach(courseID ->{
			PreparedStatement preparedStatement = null;
			try {
				String query = "INSERT INTO BookCourses(bookID , courseID) VALUES (? , ?)";
				preparedStatement = db.getConnection().prepareStatement(query);
				preparedStatement.setLong(1, bookID);
				preparedStatement.setInt(2, courseID);
				preparedStatement.executeUpdate();
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void addBookPlugins(Long bookID , ArrayList<Integer> pluginIDs){
		
		pluginIDs.forEach(pluginID ->{
			PreparedStatement preparedStatement = null;
			try {
				//System.out.println("adding relat "+relatedPluginID);
				String query = "INSERT INTO PluginBooks(pluginID , bookID) VALUES (? , ?)";
				preparedStatement = db.getConnection().prepareStatement(query);
				preparedStatement.setLong(1, pluginID);
				preparedStatement.setLong(2, bookID);
				preparedStatement.executeUpdate();
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public ArrayList<HashMap<String,String>> getBooksForPlugin(int pluginID){
		
		ArrayList<HashMap<String,String>> books = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try{
			String query = "SELECT bk.* " + 
							"FROM " + 
							"Plugins as pl " + 
							"INNER JOIN " + 
							"PluginBooks as pb " + 
							"on " + 
							"pl.pluginID = pb.pluginID " + 
							"INNER JOIN " + 
							"Books as bk " + 
							"ON " + 
							"bk.bookID = pb.bookID " + 
							"WHERE pb.pluginID = ?";
			
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
			
			ResultSet bookResult = preparedStatement.executeQuery();
			return resultSetToArrayList(bookResult);
		
		}catch(SQLException e){
			
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	public ArrayList<HashMap<String,String>> getBooksForCourse(int bookID){
		
		ArrayList<HashMap<String,String>> books = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try{
		String query = "SELECT bk.* FROM Books as bk "
				+ " INNER JOIN "
				+ " BookCourses "
				+ " ON bk.bookID = BookCourses.bookID "
				+ " WHERE BookCourses.courseID = ? ";
		
		preparedStatement = db.getConnection().prepareStatement(query);
		preparedStatement.setInt(1, bookID);
		
		ResultSet bookResult = preparedStatement.executeQuery();
		
		return resultSetToArrayList(bookResult);
		
		}catch(SQLException e){
			
		}finally{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public ArrayList<Integer> getIDsOfCode(ArrayList<String> bookCodes){
		
		ArrayList<Integer> codeIDs = new ArrayList<>();
		
		ArrayList<String> quotedCodes = new ArrayList<>();
		bookCodes.forEach(bookCode->{
			quotedCodes.add("'"+bookCode+"'");
		});
		
		String codes = String.join(",", quotedCodes);
		//TODO:check the courseIDs 4 sql injection;
		String query = "SELECT bookID FROM Books WHERE bookCode IN ("+codes+")";
		System.out.println(query);
		
		try {
			ResultSet courseResult = db.getStatement().executeQuery(query);
			while(courseResult.next()){
				codeIDs.add(courseResult.getInt("bookID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codeIDs;
	}
	
	//getSearchedBook();

}
