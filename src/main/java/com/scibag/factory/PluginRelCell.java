package com.scibag.factory;

import java.util.HashMap;

import com.scibag.controllers.PluginRelController;
import com.scibag.holders.CourseCategoryHolder;

import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class PluginRelCell extends ListCell<HashMap<String,String>>{
	
	
	PluginRelController controller;
	
	public PluginRelCell(PluginRelController controller, ListView<HashMap<String,String>> list){
		
		this.controller = controller;
		prefWidthProperty().bind(list.widthProperty().subtract(2));
		setMaxWidth(Control.USE_PREF_SIZE);
	}
	
	
	@Override
	protected void updateItem(HashMap<String, String> item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
		
		if(item != null){
			
			controller.setName(item.get("name"));
			System.out.println(item.get("name")+" the name that is gotten");
			if(item.get("type").equals("plugin")){
				String iconType = "";
				if(item.get("pluginType").equals("1")){
					iconType = "simulation";
				}else if(item.get("pluginType").equals("2")){
					iconType = "game";
				}else if(item.get("pluginType").equals("3")){
					iconType = "quiz";
				}else{
					iconType = "simulation";
				}
				controller.setIcon(iconType);
			}else if(item.get("type").equals("book")){
				controller.setIcon("book");
				
			}else if(item.get("type").equals("header")){
				
			}
			
			setGraphic(controller.getParent());
			
		}
	}
	
}
