package com.scibag.factory;


import com.scibag.controllers.CourseButtonController;
import com.scibag.holders.CourseCategoryHolder;

import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;


public class CourseCategoryCell extends ListCell<CourseCategoryHolder>{
	
	
	CourseButtonController courseBtnController;
	
	public CourseCategoryCell(ListView<CourseCategoryHolder> list) {
		// TODO Auto-generated constructor stub
		courseBtnController = new CourseButtonController();
		prefWidthProperty().bind(list.widthProperty().subtract(2));
		setMaxWidth(Control.USE_PREF_SIZE);
	}

   @Override
	protected void updateItem(CourseCategoryHolder course, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(course, empty);
	
		if(course != null){
			//categoryName.setText(item.getName());
			courseBtnController.setCourseName(course.getCourseName());
			courseBtnController.setCourseIcon(course.getCourseIcon());
			setGraphic(courseBtnController.getParent());
		}
   }
    
}
