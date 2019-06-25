package com.scibag.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class LevelSelectController{

	 	@FXML
	    private HBox levelSelect;

	    @FXML
	    private CheckBox easyChoiceCheckBox;

	    @FXML
	    private CheckBox mediumChoiceCheckBox;

	    @FXML
	    private CheckBox hardChoiceCheckBox;
	    
	    
	    @FXML
	    public void initialize(){
	    	
	    	//get default choices from properties file 
	    }
	    
	    
	    public int[] getLevelChoice(){
			
			int levelChoices[] = {0,0,0};
			
			if(easyChoiceCheckBox.isSelected()){
				levelChoices[0] = 1;
			}
			if(mediumChoiceCheckBox.isSelected()){
				levelChoices[1] = 1;
			}
			if(hardChoiceCheckBox.isSelected()){
				levelChoices[2] = 1;
			}
			
			return levelChoices;
		}

}
