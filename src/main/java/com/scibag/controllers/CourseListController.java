package com.scibag.controllers;

import com.scibag.connectors.Database;
import com.scibag.factory.CourseCategoryCell;
import com.scibag.holders.CourseCategoryHolder;
import com.scibag.models.CourseModel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CourseListController{

	/*public CourseListController() {
		super("com/scibag/fxml/course_list_inflater.fxml");
		// TODO Auto-generated constructor stub
	}*/
	

	@FXML
    private ListView<CourseCategoryHolder> courselistview;
	
	
	Database db;
	
	@FXML
	public void initialize(){
		
		System.out.println("courseListcontoller reached");
		db = new Database();
		setListContent();
	}
	
	public void setListContent(){
		CourseModel model = new CourseModel(db);
		ObservableList<CourseCategoryHolder> courseList = CourseCategoryHolder.generateList(model.getCourses());
    	
		courselistview.setItems(courseList);
		courselistview.setCellFactory(l -> new CourseCategoryCell(courselistview));
	}
	
	public ListView<CourseCategoryHolder> getCourseListView(){
		
		return this.courselistview;
	}

}
