package com.scibag.controllers;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import com.scibag.helpers.AppProperties;
import com.scibag.helpers.PluginManager;
import com.scibag.models.network.Plugin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PluginDownloadListController extends InflatableController{
	
	@FXML
    private ImageView iconImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private ImageView moreDetailsBtn;

    @FXML
    private Label coursesLabel;

    @FXML
    private Label typeAndDiffLabel;

    @FXML
    private Label developerNameLabel;

    @FXML
    private Label installCountLabel;

    @FXML
    private Button installBtn;
    
    HashMap<String , String> pluginDetails;

	public PluginDownloadListController() {
		super("com/scibag/fxml/plugin_download_list_item.fxml");
	}
	
	
	
	@FXML
	public void initialize(){
		
		
	}
	
	
	public void setPluginDetails(HashMap<String , String> pluginDetails){
		
		nameLabel.setText(pluginDetails.get("name"));
		detailsLabel.setText(pluginDetails.get("description"));
		coursesLabel.setText("("+pluginDetails.get("courseNames")+")");
		typeAndDiffLabel.setText(pluginDetails.get("type")+" "+pluginDetails.get("level"));
		developerNameLabel.setText(pluginDetails.get("developerName"));
		installCountLabel.setText(pluginDetails.get("downloadCount"));
		
		iconImageView.setImage(new Image(pluginDetails.get("pluginIconURL"), true));
		
		System.out.println(pluginDetails.get("pluginIconURL"));
		
		installBtn.setOnAction(e->{
			
			System.out.println("herree");
			//document directory tho
			
			PluginManager pManager = new PluginManager();
			String downloadedFilePath = pManager.downloadPlugin(Plugin.PLUGIN_URL_PATH+pluginDetails.get("absoluteID")+".jar");
    		pManager.installPlugin(downloadedFilePath); ///check the absolute path
    		System.out.println(pManager.isPluginInstalled());
    		if(pManager.isPluginInstalled()){
	    		Alert installAlert = new Alert(AlertType.INFORMATION , "Plugin Installed successfully!!", ButtonType.OK);
	    		installAlert.showAndWait();
    		}else{
    			Alert installAlert = new Alert(AlertType.ERROR , "Plugin Installed failed!!", ButtonType.OK);
	    		installAlert.showAndWait();
    		}
		});
	}
	
	

}
