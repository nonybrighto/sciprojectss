package com.scibag.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import com.scibag.connectors.Database;
import com.scibag.helpers.PluginManager;
import com.scibag.models.network.Plugin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PluginFileInstallController extends DialogController{

    


	@FXML
    private TextField pluginDirTextBox;

    @FXML
    private Button pluginBrowseBtn;

    @FXML
    private HBox verifyNoticeBox;

    @FXML
    private ImageView verifyNoticeImageView;

    @FXML
    private Label verifyNoticeLabel;

    @FXML
    private Button verifyBtn;

    @FXML
    private Button installBtn;
    
    
    public PluginFileInstallController() {
		super("com/scibag/fxml/plugin_file_install.fxml", false);
		// TODO Auto-generated constructor stub
	}

    @FXML
    public void initialize(){
    	
    	verifyNoticeBox.setVisible(false);
    	
    	pluginBrowseBtn.setOnAction(e->{
    		
    		
    		 Node  source = (Node)  e.getSource(); 
    		 Stage stage  = (Stage) source.getScene().getWindow();
    		
    		FileChooser fileChooser = new FileChooser();
    		fileChooser.setTitle("Open Resource File");
    		
    		File file = fileChooser.showOpenDialog(stage);
    		
    		
			pluginDirTextBox.setText(file.getAbsolutePath());
			
    	});
    	
    	
    	installBtn.setOnAction(e->{
    		
    		if(!pluginDirTextBox.getText().isEmpty()){
	    		PluginManager pManager = new PluginManager();
	    		pManager.installPlugin(pluginDirTextBox.getText());
	    		System.out.println(pManager.isPluginInstalled());
	    		if(pManager.isPluginInstalled()){
		    		Alert installAlert = new Alert(AlertType.INFORMATION , "Plugin Installed successfully", ButtonType.OK);
		    		installAlert.showAndWait();
		    		
			    		if(installAlert.getResult() == ButtonType.OK){
			    			 Node  source = (Node)  e.getSource(); 
			        		 Stage stage  = (Stage) source.getScene().getWindow();
			    			 stage.close();
			    		}
	    		}else{
	    			new Alert(AlertType.ERROR , "Error occured while installing file. Make sure file is a plugin", ButtonType.OK).showAndWait();
	    		}
    		}else{
    			new Alert(AlertType.ERROR , "Please select a file to install", ButtonType.OK).showAndWait();
    		}
    	});
    	
    	
    	verifyBtn.setOnAction(e->{
    		
    		Plugin pluginModel = new Plugin(new Database(Database.MY_SQL));
    		
    		String pluginHash = null;
			try {
				pluginHash = DigestUtils.sha1Hex(new FileInputStream(new File(pluginDirTextBox.getText())));
				
				//System.out.println(pluginHash+ " hash");
				if(pluginModel.hashPresent(pluginHash)){
					verifyNoticeBox.setVisible(true);
					verifyNoticeLabel.setText("VERIFIED");
					verifyNoticeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/ver.png")));
					verifyNoticeLabel.setStyle("-fx-color:#00ff00");
					new Alert(AlertType.INFORMATION, "The Plugin Has Been verified to be genuine!", ButtonType.OK).showAndWait();
	    		}else{
	    			verifyNoticeBox.setVisible(true);
	    			verifyNoticeLabel.setText("NOT VERIFIED");
	    			verifyNoticeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/not_ver.png")));
	    			verifyNoticeLabel.setStyle("-fx-color:#ff0000");
	    			new Alert(AlertType.INFORMATION, "This plugin is not a trusted plugin!", ButtonType.OK).showAndWait();
	    		}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				new Alert(AlertType.ERROR , "The file could not be found", ButtonType.OK).showAndWait();
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				new Alert(AlertType.ERROR , "An error occured while handling file", ButtonType.OK).showAndWait();
				e1.printStackTrace();
			}
    		
    		
    	});
    	
    }
    
}

