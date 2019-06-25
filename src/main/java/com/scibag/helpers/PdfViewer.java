package com.scibag.helpers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.scibag.controllers.PdfViewerController;

public class PdfViewer {
	
	
	
	String pdfURL;
	int view;
	
	public static final int EXTERNAL_VIEW = 0 , INTERNAL_VIEW = 1; 
	
	/*public PdfViewer(){
		
	}*/
	
	public PdfViewer(String pdfURL , int view){
		
		this.pdfURL = pdfURL;
		this.view = view;
	}
	
	
	public void showPDF(){
		
		switch(view){
		
			case PdfViewer.EXTERNAL_VIEW:
				showExternalPDF();
				break;
			case PdfViewer.INTERNAL_VIEW:
				showInternalPDF();
				break;
			default:
				
				break;
		}
	}
	
	
	public void showExternalPDF(){
		
		if(Desktop.isDesktopSupported()){
			
			try {
		        File myFile = new File(pdfURL);
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        // no application registered for PDFs
		    }
		}
	}
	
	public void showInternalPDF(){
		
		PdfViewerController pdfViewer = new PdfViewerController();
		pdfViewer.display();
		
	}
	
	

}
