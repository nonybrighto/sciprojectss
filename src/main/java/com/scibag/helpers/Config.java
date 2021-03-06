package com.scibag.helpers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	
	Properties properties;
	
	static Config myConfig;
	
	
	private Config(){
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("com/scibag/config/conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Config getConfig(){
		
		if(myConfig == null){
			myConfig = new Config();
		}
		
		return myConfig;
	}
	
	
	public String getProperty(String propertyName){
		
		return properties.getProperty(propertyName);
	}
	
	public String getProperty(String propertyName , String defaultValue){
		
		return properties.getProperty(propertyName, defaultValue);
	}
	
	
	public void setProperty(String propertyName , String value){
		OutputStream os = null;
		try {
			os = new FileOutputStream(getClass().getClassLoader().getResource("com/scibag/config/conf.properties").getPath());
			properties.setProperty(propertyName, value);
			properties.store(os, null);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	

}
