package com.scibag.controllers.dev;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DevMainController {

    @FXML
    private Label pageHeadLabel;

    @FXML
    private AnchorPane addPluginThumb;

    @FXML
    private AnchorPane viewPluginsThumb;
    
    @FXML
    private Button logoutBtn;
    
    DevController devController;
    
    
    @FXML
    public void initialize(){
    	
    	
    	
    }
    
    
    public void setClicks(DevController devController){
    	
    	addPluginThumb.setOnMouseClicked(e->{
    		
    		devController.showStackNode(DevController.DevPageStacks.UPLOAD_PAGE.getValue());
    	});
    	
    	viewPluginsThumb.setOnMouseClicked(e->{
    		
    		devController.showStackNode(DevController.DevPageStacks.PLUGIN_PAGE.getValue());
    	});
    	
    	logoutBtn.setOnAction(e->{
    		
    		devController.showStackNode(DevController.DevPageStacks.LOGIN.getValue());
    	});
    }
    
    
    public void setHeader(String headerText){
    	pageHeadLabel.setText(headerText);
    }

}
