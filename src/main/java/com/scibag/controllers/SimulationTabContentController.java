package com.scibag.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.scibag.api.SciBagPlugin;
import com.scibag.connectors.Database;
import com.scibag.factory.PluginRelCell;
import com.scibag.helpers.AppProperties;
import com.scibag.helpers.BookManager;
import com.scibag.helpers.PdfViewer;
import com.scibag.helpers.PluginManager;
import com.scibag.models.BookModel;
import com.scibag.models.PluginModel;
import com.scibag.models.network.Plugin;

import javafx.animation.TranslateTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SimulationTabContentController extends InflatableController{

	 @FXML
	    private ToolBar simToolBar;

	    @FXML
	    private AnchorPane simContent;

	    @FXML
	    private AnchorPane simDetailPane;

	    @FXML
	    private ImageView detailCloseBtn;
	    
	    @FXML
	    private Label simTitle;
	    
	    @FXML
	    private Button availableBtn;

	    @FXML
	    private Button onlineBtn;

	    @FXML
	    private StackPane listStack;

	    @FXML
	    private ListView<HashMap<String,String>> availableListView;

	    @FXML
	    private ListView<HashMap<String,String>> onlineListView;
	    
	    
	    ImageView noteIcon;
	    ImageView favIcon;
	    ImageView detailIcon;
	    
	    SciBagPlugin tabPlugin;
	    
	    PluginThumbController thumbController;
	    
	    int pluginID;
	    
	    int favState = 0;
	    
	    PluginModel pluginModel;
	    
	    boolean detailOut;
	    	
	public SimulationTabContentController(SciBagPlugin tabPlugin , PluginThumbController thumbController) {
		super("com/scibag/fxml/sim_tab_content.fxml");
		this.tabPlugin = tabPlugin;
		this.thumbController = thumbController;
		// TODO Auto-generated constructor stub
	}
	
	
	@FXML
	public void initialize(){
		
		setDefaultTools();
		listStack.getChildren().get(0).setVisible(true);
		listStack.getChildren().get(1).setVisible(false);
		
		availableBtn.setOnAction(e->{
			System.out.println("clicked");
			listStack.getChildren().get(0).setVisible(true);
			listStack.getChildren().get(1).setVisible(false);
		});
		
		onlineBtn.setOnAction(e->{
			System.out.println("online");
			listStack.getChildren().get(0).setVisible(false);
			listStack.getChildren().get(1).setVisible(true);
		});
		
		detailCloseBtn.setOnMouseClicked(e->{
			
			controlDetailBox();
		});
		
		
		
		
	}
	
	
	public void setInit(int pluginID){
		
		this.pluginID = pluginID;
		setFavState();
	}
	public void setDefaultTools(){
		pluginModel = new PluginModel(new Database());
		
		noteIcon = tabIconFactory("plugin_doc.png");
		favIcon = tabIconFactory("fav.png"); // no fav if not favourited;
		detailIcon = tabIconFactory("plugin_detail_icon.png");
		Separator separator = new Separator(Orientation.VERTICAL);
		
		
		
		noteIcon.setOnMouseClicked(e->{
			AppProperties prop = new AppProperties(AppProperties.CONF_FILE);
			PdfViewer viewer = new PdfViewer(prop.getProperty("app_home")+File.separator+"plugins"+
					File.separator+"notes"+File.separator+tabPlugin.getAbsoluteID()+".pdf",PdfViewer.EXTERNAL_VIEW);
			viewer.showPDF();
			
		});
		
		favIcon.setOnMouseClicked(e->{
			
			System.out.println("fav clicked");
			
			if(favState == 1){
				
				pluginModel.removeFav(pluginID);
			}else{
				pluginModel.addToFav(pluginID);
			}
			setFavState();
		});
		
		detailIcon.setOnMouseClicked(e->{
			
			controlDetailBox();
			
		});
		
		simToolBar.getItems().addAll(noteIcon , favIcon , detailIcon, separator);
	}
	
	
	public void controlDetailBox(){
		if(!detailOut){
			//simDetailPane.setVisible(false);
			TranslateTransition transition = new TranslateTransition(new Duration(1000) , simDetailPane);
        	transition.setByX(simDetailPane.getWidth()+30);
        	//transition.setDelay(new Duration(i * 500)); 
        	transition.play();
        	detailOut = true;
		}else{
			//simDetailPane.setVisible(true);
			TranslateTransition transition = new TranslateTransition(new Duration(1000) , simDetailPane);
        	transition.setByX(-(simDetailPane.getWidth()+30));
        	//transition.setDelay(new Duration(i * 500)); 
        	transition.play();
        	detailOut = false;
		}
	}
	
	public void setAvailableList(int pluginID){
		
		System.out.println("SETTIIIIING LIST");
		ObservableList<HashMap<String,String>> avList = FXCollections.observableArrayList();
		PluginModel pModel = new PluginModel(new Database());
		BookModel bookModel = new BookModel(new Database());
		
		ArrayList<HashMap<String,String>> relatedPlugins = pModel.getRelatedPlugins(pluginID);
		ArrayList<HashMap<String,String>> relatedBooks = bookModel.getBooksForPlugin(pluginID);
		
		if(!relatedPlugins.isEmpty()){
			System.out.println("in plugins");
			HashMap<String , String> headerMap = new HashMap<>();
			headerMap.put("type", "header");
			headerMap.put("name", "RELATED PLUGINS");
			avList.add(headerMap);
			
			relatedPlugins.forEach(plugin->{
				System.out.println("adding plugins");
				HashMap<String , String> pluginMap = new HashMap<>();
				pluginMap.put("type", "plugin");
				pluginMap.put("name", plugin.get("name"));
				pluginMap.put("pluginType", plugin.get("type"));
				pluginMap.put("absoluteID", plugin.get("absoluteID"));
				pluginMap.put("pluginID", plugin.get("pluginID"));
				avList.add(pluginMap);
			});
			
		}
		
		if(!relatedBooks.isEmpty()){
			System.out.println("in books");
			HashMap<String , String> headerMap = new HashMap<>();
			headerMap.put("type", "header");
			headerMap.put("name", "RELATED BOOKS");
			avList.add(headerMap);
			
			relatedBooks.forEach(book->{
				System.out.println("adding books");
				HashMap<String , String> pluginMap = new HashMap<>();
				pluginMap.put("type", "book");
				pluginMap.put("name", book.get("bookName"));
				pluginMap.put("bookID", book.get("bookID"));
				avList.add(pluginMap);
			});
			
		}
		
		
		//testing
		
		avList.forEach(l->{
			System.out.println(l.get("title"));
		});
		
		availableListView.setItems(avList);
		availableListView.setCellFactory(l-> new PluginRelCell(new PluginRelController(),availableListView));
		availableListView.getSelectionModel().selectedItemProperty().addListener((ob , ov , nv)->{
			
			if(nv.get("type").equals("book")){
				String bookPath = "C:\\scibag\\books\\"+nv.get("name")+".pdf";
				if(new File(bookPath).exists()){
					
					PdfViewer viewer = new PdfViewer(bookPath, PdfViewer.EXTERNAL_VIEW);
					viewer.showPDF();
					
				}else{
					Alert alert = new Alert(AlertType.ERROR, "The Book could not be found , may have been moved!!!", ButtonType.OK);
					alert.showAndWait();
				}
			}else if(nv.get("type").equals("plugin")){
				
				thumbController.addPluginTab(Integer.valueOf(nv.get("pluginID")), nv.get("absoluteID"), nv.get("name"));
			}
		});
	}
	
	
	public void setOnlineList(String absoluteID){
		
		ObservableList<HashMap<String,String>> onlineList = FXCollections.observableArrayList();
		
		
		//get items
		PluginManager pManager = new PluginManager();
		
		Plugin pluginNetworkModel = new Plugin(new Database(Database.MY_SQL));
		Plugin pluginModel = new Plugin(new Database(Database.MY_SQL) , pluginNetworkModel.getPluginID(absoluteID));
		
		ArrayList<HashMap<String , String>> relatedPlugins = pluginModel.getRelatedPluginsDetails();
		ArrayList<HashMap<String , String>> relatedBooks = pluginModel.getPluginBooksDetails();
		
		if(!relatedPlugins.isEmpty()){
			System.out.println("in plugins");
			HashMap<String , String> headerMap = new HashMap<>();
			headerMap.put("type", "header");
			headerMap.put("name", "RELATED PLUGINS");
			onlineList.add(headerMap);
			
			relatedPlugins.forEach(plugin->{
				System.out.println("adding plugins");
				HashMap<String , String> pluginMap = new HashMap<>();
				pluginMap.put("type", "plugin");
				pluginMap.put("name", plugin.get("name"));
				pluginMap.put("pluginType", plugin.get("type"));
				pluginMap.put("absoluteID", plugin.get("absoluteID"));
				pluginMap.put("pluginID", plugin.get("pluginID"));
				onlineList.add(pluginMap);
				
				System.out.println(plugin.get("name")+"---NNNNNN");
				System.out.println(plugin.get("type")+"---TTTNNNNNN");
			});
			
		}
		
		if(!relatedBooks.isEmpty()){
			System.out.println("in books");
			HashMap<String , String> headerMap = new HashMap<>();
			headerMap.put("type", "header");
			headerMap.put("name", "RELATED BOOKS");
			onlineList.add(headerMap);
			
			relatedBooks.forEach(book->{
				System.out.println("adding books");
				HashMap<String , String> pluginMap = new HashMap<>();
				pluginMap.put("type", "book");
				pluginMap.put("name", book.get("bookName"));
				pluginMap.put("bookID", book.get("bookID"));
				pluginMap.put("bookURL", book.get("bookURL"));
				pluginMap.put("bookCode", book.get("bookCode"));
				onlineList.add(pluginMap);
			});
			
		}
		
		onlineListView.setItems(onlineList);
		onlineListView.setCellFactory(l-> new PluginRelCell(new PluginRelController(),onlineListView));
		onlineListView.getSelectionModel().selectedItemProperty().addListener((ob , ov , nv)->{
			
			
			if(nv.get("type").equals("plugin")){
				
				String downloadedFilePath = pManager.downloadPlugin(Plugin.PLUGIN_URL_PATH+nv.get("absoluteID")+".jar");
	    		pManager.installPlugin(downloadedFilePath); ///check the absolute path
	    		System.out.println(pManager.isPluginInstalled());
	    		if(pManager.isPluginInstalled()){
		    		Alert installAlert = new Alert(AlertType.INFORMATION , "Plugin Installed successfully!!", ButtonType.OK);
		    		installAlert.showAndWait();
	    		}else{
	    			Alert installAlert = new Alert(AlertType.ERROR , "Plugin Installed failed!!", ButtonType.OK);
		    		installAlert.showAndWait();
	    		}
				
			}else if(nv.get("type").equals("book")){
				
				System.out.println(nv.get("bookURL")+"  feeeee");
				BookManager bManager = new BookManager();
				String path = bManager.downloadBook(nv.get("bookURL"),nv.get("bookName"));
				
				if(path != null){
					
					bManager.installBook(nv.get("bookCode"));
					if(bManager.isBookInstalled()){
						new Alert(AlertType.INFORMATION, "Book has been added!", ButtonType.OK).showAndWait();
					}else{
						new Alert(AlertType.INFORMATION, "Error while adding book!", ButtonType.OK).showAndWait();
					}
				}else{
					new Alert(AlertType.INFORMATION, "Error while adding book!", ButtonType.OK).showAndWait();
				}
				
			}
			
		});
		
	}
	
	private ImageView tabIconFactory(String iconName){
		
		ImageView tabIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/"+iconName)));
		tabIcon.setFitWidth(30);
		tabIcon.setFitHeight(30);
		
		return tabIcon;
	}
	
	private void setFavState(){
		if(pluginModel.isFav(pluginID)){
			favState = 1;
			favIcon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/fav.png")));
		}else{
			favState = 0;
			favIcon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/no_fav_blue.png")));
		}
		
	}
	
	public void setSimContent(Node content){
		
		simContent.getChildren().add(content);
	}
	
	
	

}
