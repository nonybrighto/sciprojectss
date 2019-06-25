package com.scibag.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PdfViewerController extends DialogController{

    public PdfViewerController() {
		super("com/scibag/fxml/pdf_viewer.fxml", true);
		// TODO Auto-generated constructor stub
	}

	@FXML
    private Label pdfTitle;

    @FXML
    private WebView pdfContentWebView;
    
    
    
    
    @FXML
    public void initialize(){
    	
    	System.out.println("calllllllllllll");
    	WebEngine webEngine = pdfContentWebView.getEngine();
    	webEngine.load("http://www.google.com");
    }
    
    
    
    

}

