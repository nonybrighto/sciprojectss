package com.scibag.controllers;



import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.scibag.connectors.Database;
import com.scibag.holders.PluginHolder;
import com.scibag.main.MainController;
import com.scibag.models.PluginModel;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class PluginController extends InflatableController{

    @FXML
    private HBox currSimButton;

    @FXML
    private HBox favButton;

    @FXML
    private HBox freqButton;

    @FXML
    private HBox bookPanelButton;

    @FXML
    private StackPane choiceStack;

    @FXML
    private VBox pluginChoicePanel;

    @FXML
    private TilePane simulationTilePane;

    @FXML
    private TilePane gamesTilePane;

    @FXML
    private TilePane quizTilePane;

    @FXML
    private AnchorPane freqChoicePanel;

    @FXML
    private TilePane freqChoiceTilePane;

    @FXML
    private AnchorPane favChoicePanel;

    @FXML
    private TilePane favChoiceTilePane;
    
    @FXML
    private TitledPane simulationTitledPane;
    
    @FXML
    private TitledPane gamesTitledPane;
    
    @FXML
    private TitledPane quizTitledPane;

   
    @FXML
	private CourseListController courselistviewController;
    @FXML
	private LevelSelectController levelSelectController;
    
    
    MainController mainController;
    Database  db;
   
    
   
	
	public PluginController(MainController mainController){
		
		super("com/scibag/fxml/plugin_page.fxml");
		this.mainController = mainController;
		
		 
	}
	
	@FXML
	public void initialize(){
		
		db = new Database();
		setVisibleChoice(0);
		
		bookPanelButton.setOnMouseClicked(e->{
			//
			mainController.showStackNode(MainController.PageStacks.BOOK.getValue());
		});
		
		currSimButton.setOnMouseClicked(e->{
			
			mainController.showStackNode(MainController.PageStacks.SIMULATION.getValue());
		});
		
		favButton.setOnMouseClicked(e->{
			
				displayFavPlugins();
			
		});
		
		freqButton.setOnMouseClicked(e->{
			
			displayFreqPlugins();
			
			
		});
		
		
		
    	courselistviewController.getCourseListView().getSelectionModel().
    				selectedItemProperty().addListener((ob, ov , nv)->{
    		
    		displayCoursePlugins(nv.getCourseID());
    		
    		//TODO .. USE FOR SCREENSHOT
    		
    		WritableImage img = freqChoiceTilePane.snapshot(new SnapshotParameters(), null);
			File file = new File("C:/scibag/chart.png");
			
			 try {
			        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
			    } catch (IOException ex) {
			        // TODO: handle exception here
			    }
			
    	});
    	
    	
    	subButtonHover(currSimButton, "cursim");
    	subButtonHover(favButton, "fav");
    	subButtonHover(freqButton, "freq");
    	subButtonHover(bookPanelButton, "book");
	}
	
	
	private void displayFreqPlugins(){
		
		freqChoiceTilePane.getChildren().clear();
		PluginModel pluginModel = new PluginModel(db);
		ObservableList<PluginHolder> favPluginList = PluginHolder.generateList(pluginModel.getFrequentlyUsed());
		
		
		favPluginList.forEach(freqPlugin->{
			System.out.println("doing fr");
			PluginThumbController freqPluginThumb = new PluginThumbController(mainController,freqPlugin);
			freqPluginThumb.setPluginName(freqPlugin.getName());
			freqPluginThumb.setImage();
			freqChoiceTilePane.getChildren().add(freqPluginThumb.getParent());
			
			freqPluginThumb.getPluginXbtn().setOnMouseClicked(xEvt->{
				
				xEvt.consume();
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are You sure you wan to remove this plugin from frequently used?", ButtonType.OK, ButtonType.CANCEL);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.OK){
					System.out.println("remove from freq");
					pluginModel.clearCount(freqPlugin.getPluginID());
					displayFreqPlugins();
				}
				
				
			});
		});
		setVisibleChoice(2);
		
	}
	
	private void displayFavPlugins(){
		
		favChoiceTilePane.getChildren().clear();
		PluginModel pluginModel = new PluginModel(db);
		ObservableList<PluginHolder> favPluginList = PluginHolder.generateList(pluginModel.getFavouritePlugins());
		
		
		favPluginList.forEach(favPlugin->{
			System.out.println("doing fav");
			PluginThumbController favPluginThumb = new PluginThumbController(mainController,favPlugin);
			favPluginThumb.setPluginName(favPlugin.getName());
			favPluginThumb.setImage();
			favChoiceTilePane.getChildren().add(favPluginThumb.getParent());
			
			favPluginThumb.getPluginXbtn().setOnMouseClicked(xEvt->{
				
				xEvt.consume();
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are You sure you wan to remove this plugin from favourites?", ButtonType.OK, ButtonType.CANCEL);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.OK){
					System.out.println("remove from favourite");
					pluginModel.removeFav(favPlugin.getPluginID());
					displayFavPlugins();
				}
				
				
			});
		});
		setVisibleChoice(1);
	}
	
	
	private void subButtonHover(HBox subNav, String iconName){
		
		subNav.setOnMouseEntered(e->{
			((ImageView) subNav.getChildren().get(0)).setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/subnav/"+iconName+".png")));
		});
		
		subNav.setOnMouseExited(e->{
			((ImageView) subNav.getChildren().get(0)).setImage(new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/icons/subnav/"+iconName+"_blue.png")));
		});
	}
	
	public void setVisibleChoice(int choiceIndex){
		
		choiceStack.getChildren().forEach(choice->{
			
			choice.setVisible(false);
		});
		
		choiceStack.getChildren().get(choiceIndex).setVisible(true);
	}
	
	
	int totalSims = 0, totalGames = 0 , totalQuiz = 0;
	private void displayCoursePlugins(int courseID){
		
		//get plugin
		Database db = new Database();
		PluginModel plugin = new PluginModel(db);
		ObservableList<PluginHolder> coursePluginList = PluginHolder.generateList(plugin.getCoursePlugins(courseID , levelSelectController.getLevelChoice()));
		
		
		//clear current plugin list
		
		simulationTilePane.getChildren().clear();
		gamesTilePane.getChildren().clear();
		quizTilePane.getChildren().clear();
		
		//set in category
		
		
		
		totalSims = 0; totalGames = 0 ; totalQuiz = 0;
		coursePluginList.forEach(coursePlugin->{
			
			
			PluginThumbController coursePluginThumb = new PluginThumbController(mainController,coursePlugin);
			coursePluginThumb.setPluginName(coursePlugin.getName());
			coursePluginThumb.setImage();
			switch(coursePlugin.getType()){
			
				case  1:
					simulationTilePane.getChildren().add(coursePluginThumb.getParent());
					totalSims++;
					break;
				case 2:
					gamesTilePane.getChildren().add(coursePluginThumb.getParent());
					totalGames++;
					break;
				case 3:
					quizTilePane.getChildren().add(coursePluginThumb.getParent());
					totalQuiz++;
					break;
				default:
					break;
			}
			
			coursePluginThumb.getPluginXbtn().setOnMouseClicked(e->{
				
				e.consume();
				
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are You sure you want to uninstall the plugin?", ButtonType.OK, ButtonType.CANCEL);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.OK){
					System.out.println("Uninstall plugin");
					PluginModel pluginModel = new PluginModel(new Database());
					pluginModel.removePlugin(coursePlugin.getPluginID());
					displayCoursePlugins(courseID);
				}
			});
		});
		
		
		
		simulationTitledPane.setText("simulations (" + String.valueOf(totalSims)+")");
		gamesTitledPane.setText("games ("+String.valueOf(totalGames)+")");
		quizTitledPane.setText("question problems ("+String.valueOf(totalQuiz)+")");
		
		
		setVisibleChoice(0);
		
	}
	
	
	

}
