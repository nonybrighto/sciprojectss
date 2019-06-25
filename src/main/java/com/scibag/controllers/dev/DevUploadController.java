package com.scibag.controllers.dev;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.io.FileUtils;

import com.scibag.api.SciBagPlugin;
import com.scibag.connectors.Database;
import com.scibag.connectors.FtpConnection;
import com.scibag.helpers.PluginManager;
import com.scibag.models.network.Book;
import com.scibag.models.network.Course;
import com.scibag.models.network.Plugin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DevUploadController {

    @FXML
    private ImageView pluginIconImgView;

    @FXML
    private Label pluginNameLabel;

    @FXML
    private Label pluginCategoryLabel;

    @FXML
    private Label pluginDeveloperLabel;

    @FXML
    private TextField filePathTxtField;

    @FXML
    private Button fileChooseBtn;

    @FXML
    private Button pluginUploadBtn;
    
    @FXML
    private Button viewDetailsBtn;
    
	String pluginPath;
	
	@FXML
    private ImageView devMenuBtn;
	
	DevController devController;
    
    @FXML
    public void initialize(){
    	
    	pluginPath = "";
    	
    	fileChooseBtn.setOnAction(e->{
    		
    		Node  source = (Node)  e.getSource(); 
    		Stage stage  = (Stage) source.getScene().getWindow();
    		 
    		FileChooser fileChooser = new FileChooser();
    		fileChooser.setTitle("Open Resource File");
    		
    		File file = fileChooser.showOpenDialog(stage);
			filePathTxtField.setText(file.getAbsolutePath());
			
			pluginPath = filePathTxtField.getText().trim();
			
    	});
    	
    	viewDetailsBtn.setOnAction(e->{
    		
    		if(pluginPath.isEmpty() || !new File(pluginPath).exists()){
    			
    			new Alert(AlertType.ERROR, "Please select a valid file", ButtonType.OK).showAndWait();
    		}else{
    			
    			PluginManager pluginManager = new PluginManager(PluginManager.InstallType.TEMP);
    			try {
					getPluginDetails(pluginManager);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		
    	});
    	
    	devMenuBtn.setOnMouseClicked(e->{
    		
    		devController.showStackNode(DevController.DevPageStacks.MAIN_PAGE.getValue());
    	});
    	
    	pluginUploadBtn.setOnAction(e->{
			
    		
    		if(pluginPath.isEmpty() || !new File(pluginPath).exists()){
    			
    			new Alert(AlertType.ERROR, "Please select a valid file", ButtonType.OK).showAndWait();
    		}else{
    		
	    		Database networkDB = new Database(Database.MY_SQL);
	    		
	    		
	    		try {
		    		
	    			PluginManager pluginManager = new PluginManager(PluginManager.InstallType.TEMP);
					
	    			SciBagPlugin plugin = getPluginDetails(pluginManager);
	
	    			ArrayList<String> courses = plugin.getPluginCourses();
	    			ArrayList<String> relatedBooks = plugin.getRelatedBooks();
	    			ArrayList<String> relatedPlugin = plugin.getRelatedPlugins();
	    			
					String pluginHash = DigestUtils.sha1Hex(new FileInputStream(new File(pluginPath)));
					
					System.out.println(pluginHash);
					
					Plugin pluginNetworkModel = new Plugin(networkDB);
					
					
					if(pluginNetworkModel.absoluteIdTaken(plugin.getAbsoluteID())){
						
						new Alert(AlertType.ERROR, "Plugin with this absoluteID already present",ButtonType.OK).showAndWait();
						
					}else if(pluginNetworkModel.hashPresent(pluginHash)){
						
						new Alert(AlertType.ERROR, "The plugin already Exists",ButtonType.OK).showAndWait();
						
					}else{
							
							FtpConnection ftpconnect = new FtpConnection();
							
							ftpconnect.uploadFile(pluginPath, Plugin.PLUGIN_PATH+"/"+plugin.getAbsoluteID()+".jar");
							Long addedPluginID = pluginNetworkModel.addPlugin(plugin.getName(), plugin.getAbsoluteID(), plugin.getDescription(), 
											DevController.userID, pluginHash, plugin.getType().getValue(), plugin.getDifficulty().getValue());
							
							if(addedPluginID > 0){
								
								//add icon
								
								ftpconnect.uploadFileStream(pluginManager.getPluginIconAsStream(pluginPath), Plugin.PLUGIN_PATH+"/icons/"+plugin.getAbsoluteID()+".png");
								
								Plugin addedPluginModel = new Plugin(networkDB, addedPluginID);
								
								 
								
								Course courseModel = new Course(networkDB);
								courses.forEach(courseShortCode->{
									
									addedPluginModel.addPluginCourse(courseModel.getCourseID(courseShortCode));
								});
								
								Book bookModel = new Book(networkDB);
								relatedBooks.forEach(bookCode->{
									addedPluginModel.addPluginCourse(bookModel.getBookID(bookCode));
								});
								
								relatedPlugin.forEach(absID->{
									addedPluginModel.addRelatedPlugin(pluginNetworkModel.getPluginID(absID));
								});
								
								new Alert(AlertType.INFORMATION, "Plugin has been succesfully Uploaded", ButtonType.OK).showAndWait();
								
							}else{
								new Alert(AlertType.ERROR, "Plugin Upload failed",ButtonType.OK).showAndWait();
							}
					
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
    		}
			
		});
    }
    
    public void setDevController(DevController devController){
    	this.devController = devController;
    }
    public SciBagPlugin  getPluginDetails(PluginManager pluginManager) throws IOException{
		
		
		
		
		String absoluteID = pluginManager.getPluginDetail(filePathTxtField.getText().trim(), "absoluteID");
		
		
		pluginNameLabel.setText(absoluteID);
		pluginIconImgView.setImage(new Image(pluginManager.getPluginIconAsStream(pluginPath)));
		
		pluginManager.installPlugin(pluginPath);
		
		PluginManager postInstalledManager = new PluginManager(PluginManager.InstallType.TEMP);
		return postInstalledManager.getPluginInstance(absoluteID);
		
		
}

}
