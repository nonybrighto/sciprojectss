package com.scibag.holders;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseCategoryHolder {
	
	
	
	int courseID;
	String courseName;
	String shortCode;
	String courseIcon;
	String description;
	public CourseCategoryHolder(int courseID, String courseName, String shortCode, String courseIcon, String description) {
		
		this.courseID = courseID;
		this.courseName = courseName;
		this.shortCode = shortCode;
		this.courseIcon = courseIcon;
		this.description = description;
	}
	
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseIcon() {
		return courseIcon;
	}
	public void setCourseIcon(String courseIcon) {
		this.courseIcon = courseIcon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public static ObservableList<CourseCategoryHolder> generateList(ArrayList<HashMap<String, String>> courseArrayList){
		
		
		ObservableList<CourseCategoryHolder> courseList = FXCollections.observableArrayList();
		courseArrayList.forEach(hs->{
			
			System.out.println("hhhh"+hs.get("name")+" "+hs.get("id"));
			courseList.add(new CourseCategoryHolder(Integer.valueOf(hs.get("courseID")), hs.get("name"), hs.get("shortCode"),
							hs.get("shortCode"), hs.get("description")));
		});
		
		
		return courseList;
	}

}
