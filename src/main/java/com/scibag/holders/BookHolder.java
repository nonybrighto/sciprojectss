package com.scibag.holders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookHolder {
	
	
	int bookID;
	String bookName;
	Date dateAdded;
	String authors;
	String description;
	Date lastUsed;
	public BookHolder(int bookID, String bookName, Date dateAdded, String authors, String description, Date lastUsed) {

		this.bookID = bookID;
		this.bookName = bookName;
		this.dateAdded = dateAdded;
		this.authors = authors;
		this.description = description;
		this.lastUsed = lastUsed;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	
	public static ObservableList<BookHolder> generateList(ArrayList<HashMap<String, String>> bookArrayList){
		
		ObservableList<BookHolder> bookList = FXCollections.observableArrayList();
		bookArrayList.forEach(bk->{
			
			bookList.add(new BookHolder(Integer.valueOf(bk.get("bookID")), bk.get("bookName"), null , bk.get("authors"), bk.get("description"), null));
		});
		
		
		return bookList;
	}
	
	
	

}
