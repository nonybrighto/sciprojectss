package com.scibag.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scibag.connectors.Database;

public class PluginModel extends Model{

	public PluginModel(Database db) {
		super(db);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Long addPlugin(String name, int type, String description, int difficulty , String absoluteID){
		
		String query = "INSERT INTO Plugins ('name', 'type' , 'description' , 'difficulty' ,'absoluteID') "
				+ "VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = db.getConnection().prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, type);
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, difficulty);
			preparedStatement.setString(5, absoluteID);
			
			preparedStatement.executeUpdate();
			
			 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	//System.out.println("getting keyyy "+generatedKeys.getLong(1));
		            	 return generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
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
	
	public ArrayList<HashMap<String, String>> getCoursePlugins(int courseID , int[] levelChoices){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT pl.* FROM Plugins as pl "
					+ " INNER JOIN "
					+ " PluginCourses "
					+ " ON pl.pluginID = PluginCourses.pluginID "
					+ " WHERE PluginCourses.courseID = ? "+getLevelQuery(levelChoices);
			
			System.out.println(query);
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, courseID);
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
	
	public void addPluginCourses(Long pluginID , ArrayList<Integer> courseIDs){
		
		courseIDs.forEach(courseID ->{
			PreparedStatement preparedStatement = null;
			try {
				String query = "INSERT INTO PluginCourses(pluginID , courseID) VALUES (? , ?)";
				 preparedStatement = db.getConnection().prepareStatement(query);
				preparedStatement.setLong(1, pluginID);
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
	
	
	
	public void addPluginRelationship(Long pluginID , ArrayList<Integer> relatedPluginIDs){
		
		
		System.out.println("rellllll ");
		relatedPluginIDs.forEach(relatedPluginID ->{
			PreparedStatement preparedStatement = null;
			try {
				System.out.println("adding relat "+relatedPluginID);
				String query = "INSERT INTO PluginRelationship(pluginID , relatedPluginID) VALUES (? , ?)";
				 preparedStatement = db.getConnection().prepareStatement(query);
				preparedStatement.setLong(1, pluginID);
				preparedStatement.setInt(2, relatedPluginID);
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
	
	public void addPluginBooks(Long pluginID , ArrayList<Integer> books){
		System.out.println("booooookkkk");
	
		books.forEach(book ->{
			PreparedStatement preparedStatement = null;
			try {
				System.out.println("adding book "+book);
				String query = "INSERT INTO PluginBooks(pluginID , bookID) VALUES (? , ?)";
				 preparedStatement = db.getConnection().prepareStatement(query);
				preparedStatement.setLong(1, pluginID);
				preparedStatement.setInt(2, book);
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
	
	public ArrayList<HashMap<String, String>> getRelatedPlugins(int pluginID){
		
		ArrayList<HashMap<String,String>> plugins = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT * FROM Plugins WHERE pluginID IN (SELECT relatedPluginID FROM PluginRelationship WHERE pluginID = ?) " + 
					" UNION " + 
					"SELECT * FROM Plugins WHERE pluginID IN (SELECT pluginID FROM PluginRelationship WHERE relatedPluginID = ?)";
			
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
			preparedStatement.setInt(2, pluginID);
			ResultSet pluginResult = preparedStatement.executeQuery();
			
			return resultSetToArrayList(pluginResult);
		}catch (SQLException e) {
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
	
	
	public boolean pluginExists(String absoluteID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT 1 FROM Plugins WHERE absoluteID = ?";
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
	
	public ArrayList<Integer> getIDsOfAbsoluteIDs(ArrayList<String> absoluteIDs){
		
		ArrayList<Integer> codeIDs = new ArrayList<>();
		
		ArrayList<String> quotedCodes = new ArrayList<>();
		absoluteIDs.forEach(absoluteID->{
			quotedCodes.add("'"+absoluteID+"'");
		});
		
		String codes = String.join(",", quotedCodes);
		//TODO:check the courseIDs 4 sql injection;
		String query = "SELECT pluginID FROM Plugins WHERE absoluteID IN ("+codes+")";
		System.out.println(query);
		
		try {
			ResultSet pluginResult = db.getStatement().executeQuery(query);
			while(pluginResult.next()){
				codeIDs.add(pluginResult.getInt("pluginID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codeIDs;
	}
	
	public void addToFav(int pluginID){
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Plugins SET isFav = 1 WHERE pluginID = ?";
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
		
	}
	
	public void removeFav(int pluginID){
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Plugins SET isFav = 0 WHERE pluginID = ?";
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
		
	}
	
	public boolean isFav(int pluginID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT 1 FROM Plugins WHERE pluginID = ? AND  isFav = 1";
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
	
	public void addCount(int pluginID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Plugins SET usageCount = usageCount + 1 WHERE pluginID = ?";
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
	}
	
	public void clearCount(int pluginID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "UPDATE Plugins SET usageCount = 0 WHERE pluginID = ?";
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
	}
	
	public void removePlugin(int pluginID){
		
		PreparedStatement preparedStatement = null;
		try {
			String query = "DELETE FROM Plugins WHERE pluginID = ?";
			
			 preparedStatement = db.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, pluginID);
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
	}
	
	public ArrayList<HashMap<String, String>> getFavouritePlugins(){
		
		ArrayList<HashMap<String,String>> plugins = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try {
				String query = "SELECT * FROM Plugins WHERE isFav = 1";
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
			
			return plugins;
		
	}
	
	public ArrayList<HashMap<String, String>> getFrequentlyUsed(){
		
		ArrayList<HashMap<String,String>> plugins = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try {
			String query = "SELECT * FROM Plugins WHERE usageCount > 0 order by usageCount DESC LIMIT 8";
			
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
			
			return plugins;
		
	}
	
	
	//getRelatedPlugins(int pluginID)
	//getRelatedBooks(int pluginID)
	//getSearchedPlugins(String searchString)
	
	private String getLevelQuery(int[] levelChoices){
		
		/*String query = " WHERE ";
		boolean gotten = false;
		int choiceCount = 0;
		for(int i = 0 ; i < levelChoices.length ; i++){
			
				if(levelChoices[i] == 1){
					
					query += (gotten)?" AND ":"";
					query += " difficulty = "+(i+1);
					gotten = true;
					choiceCount++;
				}
		}
		
		if(choiceCount == 0 || choiceCount == 3){
			query = "";
		}
		System.out.println("qqqq "+query);
		
		return query;*/
		
		String query = "";
		boolean gotten = false;
		int choiceCount = 0;
		for(int i = 0 ; i < levelChoices.length ; i++){
			
				if(levelChoices[i] == 1){
					query += (!gotten)?" AND (":" OR ";
					query += "  difficulty = "+(i+1);
					gotten = true;
					choiceCount++;
				}
				
		}
		
		if(choiceCount == 0 || choiceCount == 3){
			query = "";
		}else{
			query += ")";
		}
		
		return query;
	}
	
	
	
}
