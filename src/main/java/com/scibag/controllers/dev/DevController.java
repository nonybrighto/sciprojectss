package com.scibag.controllers.dev;

import java.util.HashMap;

import com.scibag.connectors.Database;
import com.scibag.controllers.DialogController;
import com.scibag.models.network.Developer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class DevController extends DialogController{

	
	@FXML
	StackPane devStackPane;
	
	@FXML
	Label loginMessageLabel;
	@FXML
	TextField usernameTxtField;
	@FXML
	TextField passwordTxtField;
	@FXML
	Button loginBtn;
	@FXML
	Label registerLinkLabel;	
	@FXML
	Label loginLinkLabel;	
	@FXML
	Label registerMessageLabel;
	@FXML
	TextField regUsernameTxtField;
	@FXML
	TextField regPasswordTxtField;
	@FXML
	TextField regEmailTxtField;
	@FXML
	TextField regUrlTxtField;
	@FXML
	Button regBtn;
	@FXML
	DevMainController devMainPageController;
	@FXML
	DevUploadController devUploadController;
	@FXML
	AddedAppController addedAppController;
	
	public static long userID;
	public static String username;
	
	Developer developer;
	
	boolean isLoggedIn;
	
	public DevController() {
		super("com/scibag/fxml/dev/developer_page.fxml", true);
	}
	
	
	@FXML
	public void initialize(){
		
		
		
		developer = new Developer(new Database(Database.MY_SQL));
		if(isLoggedIn){
			showStackNode(DevPageStacks.MAIN_PAGE.getValue()); 
		}else{
			showStackNode(DevPageStacks.LOGIN.getValue());
		}
		
		registerLinkLabel.setOnMouseClicked(e->{
			showStackNode(DevPageStacks.REGISTER.getValue());
		});
		
		loginLinkLabel.setOnMouseClicked(e->{
			showStackNode(DevPageStacks.LOGIN.getValue());
		});
		
		//remove later
		DevController.userID = 3;
		showStackNode(DevPageStacks.UPLOAD_PAGE.getValue());
		
		loginBtn.setOnAction(e->{
			
				loginUser();
		});
		
		
		regBtn.setOnAction(e->{
			
			registerUser();
		});
		
		
		devMainPageController.setClicks(this);
		devUploadController.setDevController(this);
	}
	
	
	public boolean loginUser(){
		
		if(developer.canLogin(usernameTxtField.getText(), passwordTxtField.getText())){
			
			showStackNode(DevPageStacks.MAIN_PAGE.getValue());
			DevController.userID = developer.getUserID(usernameTxtField.getText());
			DevController.username = usernameTxtField.getText().trim();
			
			devMainPageController.setHeader("Welcome to Your Panel "+DevController.username);
			usernameTxtField.setText("");
			passwordTxtField.setText("");
			
			loginMessageLabel.setText("Enter your username and password...");
			return true;
		}else{
			//TODO: ANIMATE
			loginMessageLabel.setText("Username or password Incorrect");
			return false;
		}
		
	}

	
	public void registerUser(){
		
		String username = regUsernameTxtField.getText().toString().trim().toLowerCase();
		String password = regPasswordTxtField.getText().toString();
		String email = regEmailTxtField.getText().toString().trim();
		String url = regUrlTxtField.getText().toString().trim();
		
		if(username.isEmpty() || password.isEmpty() || email.isEmpty() || url.isEmpty()){
			
			registerMessageLabel.setText("Please fill all details");
			
		}else if(developer.usernameTaken(username)){
			registerMessageLabel.setText("username taken");
		}else if(developer.emailTaken(email)){
			registerMessageLabel.setText("email taken");
		}else if(developer.urlTaken(url)){
			registerMessageLabel.setText("url has been taken");
		}else{
			
			Long userID = developer.addUser(username, email, password);
			
			if(userID > 0){
				if(developer.addDeveloper(userID, url)){
					
					showStackNode(DevPageStacks.MAIN_PAGE.getValue());
					DevController.userID = userID;
					DevController.username = username;
					devMainPageController.setHeader("Welcome to Your Panel "+DevController.username);
					
					regUsernameTxtField.setText("");
					regPasswordTxtField.setText("");
					regEmailTxtField.setText("");
					regUrlTxtField.setText("");
					
					registerMessageLabel.setText("Enter your registration details...");
					
				}else{
					registerMessageLabel.setText("Problem adding as a developer. contact admin");
				}
			}else{
				registerMessageLabel.setText("problem adding user, contact admin");
			}
			
		}
		
	}
	public static enum DevPageStacks{
		LOGIN(0) , REGISTER(1) , MAIN_PAGE(2), PLUGIN_PAGE(3),  UPLOAD_PAGE(4);
		
		private int value;
		private DevPageStacks(int value){
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
			
	};
	
	 public void showStackNode(int nodeIndex){
	    	
	    	devStackPane.getChildren().forEach(node -> {
	    		
	    		node.setVisible(false);
	    	});
	    	
	    	devStackPane.getChildren().get(nodeIndex).setVisible(true);
	    }

}
