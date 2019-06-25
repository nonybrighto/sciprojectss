package com.scibag.controllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class BookThumbController extends InflatableController{
	

	    @FXML
	    private ImageView bookIcon;

	    @FXML
	    private Label bookName;
	    
	    @FXML
	    private AnchorPane imgBox;

	    boolean isNetwork;
	public BookThumbController() {
		super("com/scibag/fxml/book_thumb.fxml");
		// TODO Auto-generated constructor stub
	}
	
	public BookThumbController(boolean isNetwork){
		super("com/scibag/fxml/book_thumb.fxml");
		this.isNetwork = isNetwork;
	}
	
	
	
	public void setBookIcon(String iconName){
		
		/*System.out.println("imgviw");
		System.out.println(bookIcon.getFitWidth());
		System.out.println(bookIcon.getFitHeight());
		
		System.out.println("imgbox");
		System.out.println(imgBox.getPrefWidth());
		System.out.println(imgBox.getPrefHeight());
		
		bookIcon.fitWidthProperty().bind(imgBox.prefWidthProperty());
		bookIcon.fitHeightProperty().bind(imgBox.prefHeightProperty());
		
		System.out.println(" new imgviw");
		System.out.println(bookIcon.getFitWidth());
		System.out.println(bookIcon.getFitHeight());*/
		
		Image img = null;
		
		if(!isNetwork){
			if(new File("C:\\scibag\\books\\icons\\"+iconName).exists()){
				
				img = new Image("C:\\scibag\\books\\icons\\"+iconName);
			}else{
				img = new Image(getClass().getClassLoader().getResourceAsStream("com/scibag/images/book_cover.png"));
			}
		
		}else{
			//img = new Image(iconName, true);
			img = new Image(iconName, 200, 230, false, false, false);
		}
		
		bookIcon.setFitWidth(200);
		bookIcon.setFitHeight(230);
		bookIcon.setPreserveRatio(false);
		bookIcon.setImage(img);
		
		
	}
	
	public void setBookName(String nameString){
		
		this.bookName.setText(nameString);
	}

}
