package com.scibag.models.network;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.Statement;
import com.scibag.connectors.Database;
import com.scibag.connectors.FtpConnection;
import com.scibag.models.Model;

public class Plugin extends Model{

	Long pluginID;
	
	public static final String PLUGIN_PATH = "/files/plugins";
	public static final String PLUGIN_URL_PATH = "ftp://"+FtpConnection.USER+":"+FtpConnection.PASSWORD+"@"+FtpConnection.HOST+"/files/plugins/";

	
	public Plugin(Database db) {
		super(db);
		
		// TODO Auto-generated constructor stub
	}
	public Plugin(Database db, Long pluginID) {
		super(db);
		this.pluginID = pluginID;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Long addPlugin(String name, String absoluteID, String description, Long developerID,String pluginHash, int type, int level){
	
		String query = "INSERT INTO Plugins(name, absoluteID, description, developerID, pluginHash, type, level, pluginIcon) " + 
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = db.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, absoluteID);
			preparedStatement.setString(3, description);
			preparedStatement.setLong(4, developerID);
			preparedStatement.setString(5, pluginHash);
			preparedStatement.setInt(6, type);
			preparedStatement.setInt(7, level);
			preparedStatement.setString(8, absoluteID+".png");
						
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
	
	public boolean addRelatedPlugin(Long relatedPluginID){
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO PluginRelationship(pluginID, relatedPluginID) VALUES (?,?)";
		
		try {
			preparedStatement = db.getConnection().prepareStatement(query);
			
			preparedStatement.setLong(1, this.pluginID);
			preparedStatement.setLong(2, relatedPluginID);
						
			preparedStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean addPluginCourse(Long courseID){
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO PluginCourses(pluginID, courseID) VALUES (?,?)";
		
		try {
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.pluginID);
			preparedStatement.setLong(2, courseID);
			preparedStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addPluginBook(Long bookID){
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO PluginBooks(pluginID, bookID) VALUES (?,?)";
		
		try {
			preparedStatement = db.getConnection().prepareStatement(query);
			
			preparedStatement.setLong(1, this.pluginID);
			preparedStatement.setLong(2, bookID);
			preparedStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	public long getPluginID(String absoluteID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT pluginID FROM Plugins WHERE absoluteID = ? LIMIT 1";
			
			System.out.println(query);
			
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, absoluteID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			while(pluginResult.next()){
				
				return pluginResult.getLong("pluginID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0l;
		
	}
	
	public boolean absoluteIdTaken(String absoluteID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT 1 FROM Plugins WHERE absoluteID = ? LIMIT 1";
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, absoluteID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			if(pluginResult.next()){
				return true;
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
		return false;
		
	}
	
	public boolean hashPresent(String pluginHash){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT 1 FROM Plugins WHERE pluginHash = ? LIMIT 1";
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setString(1, pluginHash);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			if(pluginResult.next()){
				return true;
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
		return false;
		
	}
	
	public boolean matchesHash(String pluginHash){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT 1 FROM Plugins WHERE pluginID = ? AND pluginHash = ? LIMIT 1";
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.pluginID);
			preparedStatement.setString(2, pluginHash);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			if(pluginResult.next()){
				return true;
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
		return false;
		
	}
	
	
	public ArrayList<HashMap<String , String>> getRelatedPluginsDetails(){
		
		
		ArrayList<HashMap<String , String>> pluginDetails = new ArrayList<>();
		
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT * FROM Plugins WHERE pluginID "
							+ " IN (SELECT relatedPluginID FROM PluginRelationship WHERE pluginID = ?) "+
							" UNION "+
							" SELECT * FROM Plugins WHERE pluginID "
							+ " IN (SELECT pluginID FROM PluginRelationship WHERE relatedPluginID = ?)";
		
			
			//System.out.println(query);
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.pluginID);
			preparedStatement.setLong(2, this.pluginID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			return resultSetToArrayList(pluginResult);
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
	
	
	public ArrayList<HashMap<String , String>> getPluginBooksDetails(){
		
		
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "SELECT bk.*, concat('"+Book.BOOK_PATH+"' , bk.bookCode , '.pdf') as bookURL "+
					" FROM "
					+ "Plugins as pl "
					+ "INNER JOIN PluginBooks as pb "
					+ "on "
					+ "pl.pluginID = pb.pluginID "
					+ "INNER JOIN "
					+ "Books as bk "
					+ "ON "
					+ "bk.bookID = pb.bookID "
					+ "WHERE pb.pluginID = ?";
								
		
			preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, this.pluginID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			return resultSetToArrayList(pluginResult);
			
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
	
	
	public ArrayList<HashMap<String , String>> getPopularPlugins(){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT pl.* , GROUP_CONCAT(cs.name) as courseNames , dv.username as developerName, "
					+ "concat('"+Plugin.PLUGIN_URL_PATH+"' , 'icons/', pl.pluginIcon) as pluginIconURL " 
					+ "FROM Plugins as pl "
					+ "INNER JOIN "
					+ "PluginCourses as plcs "
					+ "ON "
					+ "pl.pluginID = plcs.pluginID "
					+ "INNER JOIN  "
					+ "Courses as cs "
					+ "ON  "
					+ "cs.courseID = plcs.courseID "
					+ "INNER JOIN "
					+ "Users as dv "
					+ "ON  "
					+ "dv.userID = pl.developerID "
					+ "GROUP BY pl.pluginID";
		
			preparedStatement = db.getConnection().prepareStatement(query);
			ResultSet pluginResult = preparedStatement.executeQuery();
			return resultSetToArrayList(pluginResult);
			
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
	

}
