package com.scibag.factory;

import java.util.HashMap;

import com.scibag.controllers.PluginDownloadListController;

import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class DownloadItemCell extends ListCell<HashMap<String,String>>{
	
	PluginDownloadListController controller;
	
	public DownloadItemCell(PluginDownloadListController controller, ListView<HashMap<String,String>> list){
		
		this.controller = controller;
		prefWidthProperty().bind(list.widthProperty().subtract(2));
		setMaxWidth(Control.USE_PREF_SIZE);
	}
	
	
	
	@Override
	protected void updateItem(HashMap<String, String> item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
		
		if(item != null){
			
			controller.setPluginDetails(item);
			setGraphic(controller.getParent());
		}
	}

}
