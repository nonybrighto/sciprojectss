package com.scibag.controllers;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scibag.connectors.Database;
import com.scibag.factory.DownloadItemCell;
import com.scibag.models.CourseModel;
import com.scibag.models.network.Plugin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PluginDownloadController extends DialogController{
	
	
	


	@FXML
    private TextField searchTextBox;

    @FXML
    private ChoiceBox<String> courseChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private ChoiceBox<String> pluginTypeChoiceBox;

    @FXML
    private Label searchTitleLabel;

    @FXML
    private ListView<HashMap<String,String>> searchListView;

    @FXML
    private Button cancelBtn;	
    
    
    public PluginDownloadController() {
		super("com/scibag/fxml/plugin_download.fxml", false);
	}

    @FXML
    public void initialize(){
    	
    	
    	CourseModel courseModel = new CourseModel(new Database());
    	
    	ArrayList<HashMap<String , String>> coursesList = courseModel.getCourses();
    	ArrayList<String> courseNames = new ArrayList<>();
    	
    	coursesList.stream().forEach(course->{
    		courseNames.add(course.get("name"));
    	});
    	
    	courseChoiceBox.setItems(FXCollections.observableArrayList(courseNames));
    	courseChoiceBox.getSelectionModel().select(0);
    	
    	levelChoiceBox.setItems(FXCollections.observableArrayList("easy", "medium", "hard"));
    	levelChoiceBox.getSelectionModel().select(0);
    	
    	pluginTypeChoiceBox.setItems(FXCollections.observableArrayList("simulation", "games", "question problem"));
    	pluginTypeChoiceBox.getSelectionModel().select(0);
    	
    	System.out.println("done init");
    	cancelBtn.setOnAction(e->{
    		
    		System.out.println("done");
    		//stage.hide();
    		//Platform.exit();
    		 Node  source = (Node)  e.getSource(); 
    	      
    		 Stage stage  = (Stage) source.getScene().getWindow();
    		// stage.getOnCloseRequest().handle(null);
    		 stage.close();
    	});
    	
    	
    	PluginDownloadTask plTask = new PluginDownloadTask();
    	Thread th = new Thread(plTask);
    	th.start();
    	
    	plTask.setOnSucceeded(e->{
    		
    		ObservableList<HashMap<String , String>> pluginObList = FXCollections.observableArrayList();
    		pluginObList.addAll(plTask.getValue());
			
    		searchListView.setItems(pluginObList);
    		searchListView.setCellFactory(l-> new DownloadItemCell(new PluginDownloadListController(), searchListView));
    		
    		//pluginObList.forEach(pluginHashMap->{

    			//PluginDownloadListController downloadListItem = new PluginDownloadListController(pluginHashMap);
    			//downloadListItem.setPluginDetails();
    			
    			//downloadListItem.getParent().setOnMouseClicked(ev->{
					
					//System.out.println(pluginHashMap.get("pluginIconURL"));
					
				//});
    			//((AnchorPane) downloadListItem.getParent()).prefWidthProperty().bind(searchListView.widthProperty());
    			//searchListView.getItems().add(downloadListItem.getParent());
    			//((AnchorPane) downloadListItem.getParent()).prefWidthProperty().bind(searchListView.widthProperty().subtract(2));
    			
    		//});
			
    	});
    	
    }
    
    
    
    
    
    private class PluginDownloadTask extends Task<ArrayList<HashMap<String, String>>>{

		@Override
		protected ArrayList<HashMap<String, String>> call(){
			
			
			Plugin pluginNetworkModel = new Plugin(new Database(Database.MY_SQL));
			
			return pluginNetworkModel.getPopularPlugins();
			
		}
    	
    }
    
}
