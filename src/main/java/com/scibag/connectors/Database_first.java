package com.scibag.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database_first {
		
	
		final int MY_SQL = 0 , SQLITE = 1;
		
		static final String DATABASE_URL = "jdbc:mysql://localhost/scibag";
		static final String DATABASE_USERNAME = "root";
		static final String DATABASE_PASSWORD = "tested";
	
		 private Connection myCon = null;
	     private Statement myStmt = null;
	       
	    
	     int connectionType;
	        
	        
	        public Database_first(){
	        	
	        	connectionType = SQLITE;
	           // connect(location); 
	        	connect(getClass().getClassLoader().getResource("com/scibag/db/scibag.sqlite").getPath());
	            
	        }
	        
	        public Database_first(int connectionType){
	        	
	        	this.connectionType = connectionType;
	        	
	        	connect(null);
	        }
	        
	        
	        private void connect(String location){
	            
	           try {
	        	   switch(connectionType){
	        	   
		        	   case SQLITE:
		        		   //Class.forName("org.sqlite.JDBC");
			               myCon = DriverManager.getConnection("jdbc:sqlite:"+location); 
			               myStmt = myCon.createStatement();
		        		   break;
		        	   case MY_SQL:
		        		   myCon = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		        		   myStmt = myCon.createStatement();
		        		   break;
		        		default:
		        			break;
	        	   }
	            
	            } catch (SQLException ex) {
	                Logger.getLogger(Database_first.class.getName()).log(Level.SEVERE, null, ex);
	                System.out.println("ERROR");
	            }
	        }

			public Statement getStatement() {
				return myStmt;
			}
			
			public Connection getConnection(){
				return myCon;
			}
	        
	        
}
