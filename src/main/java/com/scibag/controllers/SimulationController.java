package com.scibag.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class SimulationController extends InflatableController{

	
	@FXML
    private TabPane simTabPane;
	
	public SimulationController() {
		super("com/scibag/fxml/sim_page.fxml");
		// TODO Auto-generated constructor stub
	}
	
	
	@FXML
	public void initialize(){
		
		simTabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
	}
	
	
	public TabPane getSimTabPane() {
		return simTabPane;
	}

	public boolean hasRunningTabs(){
		
		return true;
	}
	

}
