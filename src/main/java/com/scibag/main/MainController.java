package com.scibag.main;

import java.io.IOException;

import com.scibag.connectors.Database;
import com.scibag.controllers.AboutPageController;
import com.scibag.controllers.BookController;
import com.scibag.controllers.PluginController;
import com.scibag.controllers.PluginDownloadController;
import com.scibag.controllers.PluginFileInstallController;
import com.scibag.controllers.SimulationController;
import com.scibag.controllers.dev.DevController;
import com.scibag.models.network.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private ImageView mainMenuButton;

    @FXML
    private Label mainMenuTitle;
    
    @FXML
    private StackPane pageStack;
    
    PluginController pluginController;
    
    BookController bookController;

	SimulationController simulationController;
	
	@FXML
	private MenuItem pluginFileMenuItem;

	@FXML
	private MenuItem pluginDownloadMenuItem;
	
	@FXML
	private MenuItem devPageMenuItem;
	
	@FXML
	private MenuItem aboutMenuItem;
	
	public static enum PageStacks{
		PLUGIN(0) , SIMULATION(1) , BOOK(2);
		
		private int value;
		private  PageStacks(int value){
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
	
			
	};
    
    @FXML
    public void initialize(){
    	
    	//Font.loadFont(getClass().getClassLoader().getResource("com/scibag/fonts/TRON.ttf").toExternalForm(), 10);
    	//Font.loadFont(getClass().getClassLoader().getResource("com/scibag/fonts/The Bully_PersonalUseOnly.ttf").toExternalForm(), 10);
    	
    	pluginController = new PluginController(this);
    	bookController = new BookController(this);
    	simulationController = new SimulationController();
    	
    	pageStack.getChildren().add(pluginController.getParent());
    	pageStack.getChildren().add(simulationController.getParent());
    	pageStack.getChildren().add(bookController.getParent());
    	
    	
    	
    	//User usr = new User(new Database(Database.MY_SQL));
    	//usr.addUser("ddd", "dddd", "dddd");
    	
    	
    	//System.out.println(usr.canLogin("ddd", "ddddz"));
    	
    	mainMenuButton.setOnMouseClicked(e->{
    		
    		showStackNode(PageStacks.PLUGIN.getValue());
    		
    	});
    	
    	mainMenuButton.setOnMouseEntered(e->{
    		mainMenuButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/menu.png")));
    	});
    	mainMenuButton.setOnMouseExited(e->{
    		mainMenuButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/menu_blue.png")));
    	});
    	
    	showStackNode(PageStacks.PLUGIN.getValue());
    	
    	
    	pluginDownloadMenuItem.setOnAction(e->{
    		
    		PluginDownloadController downloadController = new PluginDownloadController();
    		
    		downloadController.display();
    	});
    	
    	
    	pluginFileMenuItem.setOnAction(e->{
    		
    		PluginFileInstallController fileInstall = new PluginFileInstallController();
    		fileInstall.display();
    	});
    	
    	
    	devPageMenuItem.setOnAction(e->{
    		
    		DevController devPage = new DevController();
    		devPage.display();
    	});
    	
    	aboutMenuItem.setOnAction(e->{
    		AboutPageController aboutPage = new AboutPageController();
    		aboutPage.display();
    	});
    	
    }
    
    public PluginController getPluginController() {
		return pluginController;
	}

	public SimulationController getSimulationController() {
		return simulationController;
	}
    
    public void setMenuTitle(String title){
    	
    	this.mainMenuTitle.setText(title);
    }
    
    public void showStackNode(int nodeIndex){
    	
    	pageStack.getChildren().forEach(node -> {
    		
    		node.setVisible(false);
    	});
    	
    	pageStack.getChildren().get(nodeIndex).setVisible(true);
    }
    
    public void display(){
    	
    	Parent root = null;
		try {
			//ClassLoader classLoader = getClass().getClassLoader();
		    root = FXMLLoader.load(getClass().getClassLoader().getResource("com/scibag/fxml/main_page.fxml"));
		    
		  // System.out.println(getClass().getClassLoader().getResource("com/scibag/fxml/main_page.fxml").getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/scibag/css/blue_theme.css").toString());
        
        stage.setHeight(700);
        stage.setScene(scene);
        stage.setOnHidden(e -> Platform.exit());
        stage.show();
    }
    
    @FXML
    void changeMenu(MouseEvent event) {

    	showStackNode(PageStacks.PLUGIN.getValue());
    }

    

}
