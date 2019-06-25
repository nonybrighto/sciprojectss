package com.scibag.controllers;

import java.io.File;
import java.net.MalformedURLException;

import com.scibag.api.SciBagPlugin;
import com.scibag.connectors.Database;
import com.scibag.helpers.AppProperties;
import com.scibag.helpers.DraggableTab;
import com.scibag.helpers.PluginManager;
import com.scibag.holders.PluginHolder;
import com.scibag.main.MainController;
import com.scibag.models.PluginModel;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class PluginThumbController extends InflatableController{

	 @FXML
	    private StackPane pluginBoxHolder;

	    @FXML
	    private ImageView pluginImage;

	    @FXML
	    private Label pluginDifficulty;

	    @FXML
	    private Label pluginName;

	    @FXML
	    private ImageView pluginFavImage;

	    @FXML
	    private Label pluginXbtn;

	    
	    MainController mainController;
	    
	    PluginHolder thumbPluginHolder;


	public PluginThumbController(MainController mainController , PluginHolder thumbPluginHolder) {
		super("com/scibag/fxml/plugin_thumb.fxml");
		// TODO Auto-generated constructor stub
		
		this.mainController = mainController;
		this.thumbPluginHolder = thumbPluginHolder;
		System.out.println(thumbPluginHolder.getName()+" uuuuu");
	}
	
	
	
	
	@FXML
	public void initialize(){
		
		pluginXbtn.setVisible(false);
		
		pluginBoxHolder.setOnMouseEntered(e->{
			pluginXbtn.setVisible(true);
		});
		pluginBoxHolder.setOnMouseExited(e->{
			pluginXbtn.setVisible(false);
		});
		
		

		pluginBoxHolder.setOnMouseClicked(e->{
			
			
				//pluginBoxHolder.setVisible(false);
			addPluginTab(thumbPluginHolder.getPluginID(),thumbPluginHolder.getAbsoluteID(),thumbPluginHolder.getName());
			
		});
		
		/*pluginXbtn.setOnMouseClicked(e->{
			e.consume();
			System.out.println("X button has been clicked");
		});*/
		
		
	}
	
	public Label getPluginXbtn(){
		return this.pluginXbtn;
	}
	
	public void addPluginTab(int pluginID , String absoluteID , String name){
    	
    	PluginManager pManager = new PluginManager();
    	
    	SciBagPlugin plugin =  pManager.getPluginInstance(absoluteID);
    	
    	if(plugin != null){
    		DraggableTab tab = new DraggableTab(name);
        	//tab.setText(name);
        	SimulationTabContentController simCtrl = new SimulationTabContentController(plugin,this);
        	simCtrl.setInit(pluginID);
        	simCtrl.setAvailableList(pluginID); //TODO make backgroud task
        	simCtrl.setOnlineList(absoluteID); System.out.println(absoluteID + " ADDDD");
    		
        	plugin.mainSheet(getClass().getClassLoader().getResource("com/scibag/css/blue_theme.css").toString());
        	
        	Node pluginNode = plugin.getGraphics();
        	
        	AnchorPane.setTopAnchor(pluginNode, 0d);
        	AnchorPane.setBottomAnchor(pluginNode, 0d);
        	AnchorPane.setLeftAnchor(pluginNode, 0d);
        	AnchorPane.setRightAnchor(pluginNode, 0d);
        	simCtrl.setSimContent(pluginNode);
    		tab.setContent(simCtrl.getParent());
    		tab.setClosable(true);
    		
    		TabPane simPane = mainController.getSimulationController().getSimTabPane();
    		simPane.getTabs().add(tab);
    		
    		SingleSelectionModel<Tab> selectionModel = simPane.getSelectionModel();
    		selectionModel.select(tab);
    		mainController.showStackNode(1);
    		
    		
    		PluginModel pluginModel = new PluginModel(new Database());
    		pluginModel.addCount(pluginID);
    	}else{
    		
    		new Alert(AlertType.ERROR, "Error loading plugin .. not found", ButtonType.OK).showAndWait();
    	}
    	
    	
	}

	
	public void setImage(){
		
		AppProperties prop = new AppProperties(AppProperties.CONF_FILE);
		String imagePath = prop.getProperty("app_home")+File.separator+"plugins"+File.separator+"icons"+File.separator+thumbPluginHolder.getAbsoluteID()+".png";
		if(new File(imagePath).exists()){
			try {
				this.pluginImage.setImage(new Image(new File(imagePath).toURI().toURL().toString()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(thumbPluginHolder.getIsFav() == 1){
			pluginFavImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/fav.png")));
		}
		
		switch(thumbPluginHolder.getDifficulty()){
		
			case 1:
				pluginDifficulty.getStyleClass().add("easy");
				break;
			case 2:
				//pluginDifficulty.setStyle("-fx-text-fill: blue");
				pluginDifficulty.getStyleClass().add("medium");
				break;
			case 3:
				pluginDifficulty.getStyleClass().add("hard");
				
				break;
		}
	}
	

	
	public void setPluginName(String name){
		this.pluginName.setText(name);
	}
	
	 public PluginHolder getThumbPluginHolder() {
			return thumbPluginHolder;
		}




		public void setThumbPluginHolder(PluginHolder thumbPluginHolder) {
			this.thumbPluginHolder = thumbPluginHolder;
		}

	
	
}
