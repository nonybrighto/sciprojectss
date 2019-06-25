package com.scibag.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

abstract class Controller {
	
	private Parent parent;
	
	
	public Controller(String resourceString){
		
		setParent(resourceString);
	}
	
	
	
private void setParent(String resourceString){
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resourceString));
    	loader.setController(this);

    	try {
		    parent = loader.load();
			//parent = FXMLLoader.load(file.toURI().toURL());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Parent getParent(){
		
		return this.parent;
	}
		
	

}
