package com.scibag.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class ScibagApp extends Application{

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MainController mainController = new MainController();
		mainController.display();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);
	}

}
