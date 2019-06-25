package com.scibag.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scibag.connectors.Database;

public class Model {
	
	protected Database db;
	public Model(Database db){
		
		this.db = db;
	}
	
	
	public HashMap<String, String> resultToMap(ResultSet rs) throws SQLException{
		
		ResultSetMetaData meta = rs.getMetaData();
		HashMap<String, String> map = new HashMap<>();
        while (rs.next()) {
        	
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String key = meta.getColumnName(i);
                String value = rs.getString(key);
                map.put(key, value);
            }
            
        }
        
        return map;     
	}
	
	public ArrayList<HashMap<String, String>> resultSetToArrayList(ResultSet rs) throws SQLException{
		  ResultSetMetaData md = rs.getMetaData();
		  int columns = md.getColumnCount();
		  ArrayList<HashMap<String, String>> list = new ArrayList<>();
		  
		  while (rs.next()){
		     HashMap<String, String> row = new HashMap<>(columns);
		     for(int i=1; i<=columns; ++i){           
		      row.put(md.getColumnName(i),rs.getString(i));
		     }
		      list.add(row);
		  }

		 return list;
	}
	
	public ArrayList<String> resultSetColumnList(ResultSet rs, String columnLabel) throws SQLException{
		  
		ArrayList<String> list = new ArrayList<>();
		  
		  while (rs.next()){
		    
		      list.add(rs.getString(columnLabel));
		  }
		 return list;
	}

}
