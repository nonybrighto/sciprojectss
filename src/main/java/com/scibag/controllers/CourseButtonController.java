package com.scibag.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CourseButtonController extends InflatableController{

	@FXML
    private ImageView courseIcon;

    @FXML
    private Label courseName;
    
    @FXML
    private AnchorPane container;
	
    String iconName;
	public CourseButtonController() {
		super("com/scibag/fxml/course_thumb.fxml");
		// TODO Auto-generated constructor stub
	}
	
	@FXML
	public void initialize(){
		
		container.setOnMouseEntered(e->{
			this.courseIcon.setImage(new Image(CourseButtonController.class.getClassLoader().getResourceAsStream("com/scibag/images/icons/course/"+this.iconName+"_blue.png")));
		});
		
		container.setOnMouseExited(e->{
			this.courseIcon.setImage(new Image(CourseButtonController.class.getClassLoader().getResourceAsStream("com/scibag/images/icons/course/"+this.iconName+".png")));
		
		});
		
	}

	public ImageView getCourseIcon() {
		return courseIcon;
	}

	public void setCourseIcon(String courseIcon) {
		this.iconName = courseIcon;
		this.courseIcon.setImage(new Image(CourseButtonController.class.getClassLoader().getResourceAsStream("com/scibag/images/icons/course/"+courseIcon+".png")));
	
	}


	public void setCourseName(String courseName) {
		this.courseName.setText(courseName); 
	}
	
	

}
