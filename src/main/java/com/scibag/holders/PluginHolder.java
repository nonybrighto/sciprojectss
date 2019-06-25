package com.scibag.holders;

/*
 * Refactor and use hashmap like it was done in plugindownloadlistcontroller
 */
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PluginHolder {
	
	int pluginID;
	String name;
	String absoluteID;
	int type;
	String description;
	String dateAdded;
	int difficulty;
	int isFav;
	
	public PluginHolder(int pluginID, String name, String absoluteID, int type, String description, String dateAdded, int difficulty, int isFav) {
		super();
		this.pluginID = pluginID;
		this.name = name;
		this.absoluteID = absoluteID;
		this.type = type;
		this.description = description;
		this.dateAdded = dateAdded;
		this.difficulty = difficulty;
		this.isFav = isFav;
	}
	
	

	public int getPluginID() {
		return pluginID;
	}

	public void setPluginID(int pluginID) {
		this.pluginID = pluginID;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String getAbsoluteID() {
		return absoluteID;
	}

	public void setAbsoluteID(String absoluteID) {
		this.absoluteID = absoluteID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getIsFav(){
		return this.isFav;
	}
	
	public static ObservableList<PluginHolder> generateList(ArrayList<HashMap<String, String>> pluginArrayList){
		
		
		ObservableList<PluginHolder> pluginList = FXCollections.observableArrayList();
		pluginArrayList.forEach(hs->{
			
			//System.out.println("hhhh"+hs.get("name")+" "+hs.get("id"));
			pluginList.add(new PluginHolder(Integer.valueOf(hs.get("pluginID")), hs.get("name"), hs.get("absoluteID"), Integer.valueOf(hs.get("type")), hs.get("description"), hs.get("dateAdded"), Integer.valueOf(hs.get("difficulty")), Integer.valueOf(hs.get("isFav"))));
		});
		
		
		return pluginList;
	}
	
	
	

}
