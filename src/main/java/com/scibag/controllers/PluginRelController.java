package com.scibag.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PluginRelController extends InflatableController{

   

	@FXML
    private ImageView fileTypeIconImageView;

    @FXML
    private Label fileNameLabel;

    public PluginRelController() {
		super("com/scibag/fxml/plugin_rel_thumb.fxml");
		// TODO Auto-generated constructor stub
	}
    
    
    public void setName(String name){
    	
    	fileNameLabel.setText(name);
    }
    
    public void setIcon(String type){
    	
    	this.fileTypeIconImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/plugin_type/"+type+".png")));
    }
}

