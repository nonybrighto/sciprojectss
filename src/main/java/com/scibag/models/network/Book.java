package com.scibag.models.network;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scibag.connectors.Database;
import com.scibag.connectors.FtpConnection;
import com.scibag.models.Model;

public class Book extends Model{

	public static final String BOOK_PATH = "ftp://"+FtpConnection.USER+":"+FtpConnection.PASSWORD+"@"+FtpConnection.HOST+"/files/books/";

	Long bookID;
	public Book(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	public Book(Database db, Long bookID) {
		
		super(db);
		this.bookID = bookID;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public HashMap<String, String> getBookDetails(){
		
		try {
			String query  = "SELECT * FROM Books WHERE bookID = ?";
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.bookID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			return resultToMap(pluginResult);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public ArrayList<String> getBookPlugins(){
		
		try {
			String query  = "SELECT plg.absoluteID "
					+ "FROM "
					+ "Plugins as plg "
					+ "INNER JOIN "
					+ "PluginBooks as plbk "
					+ "ON "
					+ "plg.pluginID = plbk.pluginID "
					+ "WHERE "
					+ "plbk.bookID = ?";
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.bookID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			return resultSetColumnList(pluginResult, "absoluteID");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public ArrayList<String> getBookCourses(){
		
		try {
			String query  ="SELECT cs.shortCode  "
					+ "FROM Courses as cs "
					+ "INNER JOIN "
					+ "BookCourses  as bkcs "
					+ "ON "
					+ "cs.courseID = bkcs.courseID "
					+ "WHERE "
					+ "bkcs.bookID = ?";
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.bookID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			return resultSetColumnList(pluginResult, "shortCode");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public ArrayList<HashMap<String, String>> getBooksForCourse(long courseID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT bk.* , concat('"+Book.BOOK_PATH+"' ,'icons/', bk.bookIcon) as bookIconURL , "
					+ "concat('"+Book.BOOK_PATH+"', bk.bookCode , '.pdf') as bookURL "
					+ "FROM Books as bk "
					+ "INNER JOIN "
					+ "BookCourses as bkcs "
					+ "ON "
					+ "bk.bookID = bkcs.bookID "
					+ "INNER JOIN "
					+ "Courses as cs "
					+ "ON "
					+ "cs.courseID = bkcs.courseID "
					+ "AND cs.courseID = ?";
			
			
			System.out.println(query);
		
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, courseID);
			ResultSet bookResult = preparedStatement.executeQuery();
			
			return resultSetToArrayList(bookResult);
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
		return null;
		
	}
	
	
	public long getBookID(String bookCode){
		
		try {
			String query = "SELECT bookID FROM Books WHERE bookCode = ? LIMIT 1";
			
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, bookCode);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			while(pluginResult.next()){
				
				return pluginResult.getLong("bookID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0l;
		
	}
	
	
}
