package com.scibag.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scibag.connectors.Database;
import com.scibag.helpers.BookManager;
import com.scibag.helpers.PdfViewer;
import com.scibag.holders.BookHolder;
import com.scibag.main.MainController;
import com.scibag.models.BookModel;
import com.scibag.models.PluginModel;
import com.scibag.models.network.Book;
import com.scibag.models.network.Course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class BookController extends InflatableController {

	@FXML
	private StackPane choiceStack;

	@FXML
	private TilePane bookTile;

	@FXML
	private ChoiceBox<String> viewChoiceBox;

	@FXML
	private CourseListController courselistviewController;

	@FXML
	private LevelSelectController levelSelectController;

	MainController mainController;
	Database db;

	int currentCategory;

	public BookController(MainController mainController) {

		super("com/scibag/fxml/book_page.fxml");
		this.mainController = mainController;

	}

	@FXML
    public void initialize(){
    	
    	db = new Database();
    	System.out.println("Book Controller reached");
    	
    	if(viewChoiceBox == null){
    		System.out.println("null");
    	}else{
    		System.out.println("not null");
    	}
    	
    	setViewChoice();
    	
    	courselistviewController.getCourseListView().getSelectionModel().
    						selectedItemProperty().addListener((ob , ov , nv)->{
    							
    							bookTile.getChildren().clear();
    							if(currentCategory == 0){
    								
    								//getNormal book
    								BookModel bookModel = new BookModel(db);
    								
    								ObservableList<BookHolder> bookList = BookHolder.generateList(bookModel.getBooksForCourse(nv.getCourseID()));
    								bookList.forEach(courseBook->{
    									
    									BookThumbController bookThumb = new BookThumbController();
    									bookThumb.setBookIcon(courseBook.getBookName()+".jpg"); 
    									bookThumb.setBookName(courseBook.getBookName());
    									bookThumb.getParent().setOnMouseClicked(e->{
    										
    										
    										String bookPath = "C:\\scibag\\books\\"+courseBook.getBookName()+".pdf";
    										if(new File(bookPath).exists()){
    											
    											PdfViewer viewer = new PdfViewer(bookPath, PdfViewer.EXTERNAL_VIEW);
    											viewer.showPDF();
    											
    										}else{
    											Alert alert = new Alert(AlertType.ERROR, "The Book could not be found , may have been moved!!!", ButtonType.OK);
    											alert.showAndWait();
    										}
    									});
    									bookTile.getChildren().add(bookThumb.getParent());
    								});
    								//setVisibleChoice(1);
    							}else if(currentCategory == 1){
    								
    								//book download;
    								System.out.println("dddd");
    						
										NetworkBookTask networkBookTask = new NetworkBookTask(nv.getShortCode());
										
										Thread th = new Thread(networkBookTask);
										th.start();
										
										networkBookTask.setOnSucceeded(e->{
											
											System.out.println("--------------------- here ----");
											
											ObservableList<HashMap<String , String>> bookObList = FXCollections.observableArrayList();
											bookObList.addAll(networkBookTask.getValue());
											
											bookObList.forEach(book->{
												
												BookThumbController bookThumb = new BookThumbController(true);
		    									bookThumb.setBookIcon(book.get("bookIconURL")); 
		    									bookThumb.setBookName(book.get("bookName"));
		    									bookThumb.getParent().setOnMouseClicked(ev->{
		    										
		    										//download book from
		    										
		    										System.out.println(book.get("bookCode"));
		    										
		    										BookManager bManager = new BookManager();
		    										String path = bManager.downloadBook(book.get("bookURL"),book.get("bookName"));
		    										
		    										if(path != null){
		    											
		    											bManager.installBook(book.get("bookCode"));
		    											if(bManager.isBookInstalled()){
		    												new Alert(AlertType.INFORMATION, "Book has been added!", ButtonType.OK).showAndWait();
		    											}else{
		    												new Alert(AlertType.INFORMATION, "Error while adding book!", ButtonType.OK).showAndWait();
		    											}
		    										}else{
		    											new Alert(AlertType.INFORMATION, "Error while adding book!", ButtonType.OK).showAndWait();
		    										}
		    									});
		    									bookTile.getChildren().add(bookThumb.getParent());
		    								});
										});
										
										
    							}	
										
    									
    						});
    	
	}

	public void setViewChoice() {

		viewChoiceBox.setItems(FXCollections.observableArrayList("MY BOOKS", "DOWNLOAD BOOKS"));

		viewChoiceBox.getSelectionModel().select(0);
		currentCategory = 0;

		viewChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ob, ov, nv) -> {

			currentCategory = nv.intValue();

			System.out.println(currentCategory);
		});

	}

	private class LocalBookTask extends Task<String> {

		@Override
		protected String call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private class NetworkBookTask extends Task<ArrayList<HashMap<String, String>>> {

		String shortCode;

		public NetworkBookTask(String shortCode) {
			// TODO Auto-generated constructor stub

			this.shortCode = shortCode;
		}

		@Override
		protected ArrayList<HashMap<String, String>> call() throws Exception {
			// TODO Auto-generated method stub
			Database db = new Database(Database.MY_SQL);
			Book bookNetworkModel = new Book(db);
			
			Course courseNetworkModel = new Course(db);
			return bookNetworkModel.getBooksForCourse(courseNetworkModel.getCourseID(shortCode));
		}
		
		
		@Override
		protected void succeeded() {
			// TODO Auto-generated method stub
			super.succeeded();
			
			System.out.println("succeeded");
		}

	}

}
