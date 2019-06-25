package com.scibag.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DialogController extends Controller{
	
	boolean isResizable;
	
	public DialogController(String resourceString , boolean isResizable) {
		super(resourceString);
		// TODO Auto-generated constructor stub
		this.isResizable = isResizable;
	}

	public void display(){
    	
		Stage stage = new Stage();
        Scene scene = new Scene(super.getParent());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/scibag/css/blue_theme.css").toString());
        
        
        stage.setScene(scene);
        stage.setResizable(isResizable);
        stage.show();
    }

}
