package com.scibag.helpers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import com.scibag.connectors.Database;
import com.scibag.models.BookModel;
import com.scibag.models.CourseModel;
import com.scibag.models.PluginModel;
import com.scibag.models.network.Book;

public class BookManager {
	
	
	boolean bookInstalled;
	boolean hasError;
	
	
	public String downloadBook(String URLString , String bookName){
		
		File downloadedBook = null;
		try {
			URL url = new URL(URLString);
			AppProperties prop = new AppProperties(AppProperties.CONF_FILE);
			FileUtils.copyURLToFile(url, 
					downloadedBook = new File(prop.getProperty("app_home")+File.separator+"books"+File.separator+bookName+".pdf"));
		}  catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			hasError = true;
			return null;
			
		}
		return downloadedBook.getAbsolutePath();
		
	}
	
	public boolean isBookInstalled(){
		return this.bookInstalled;
	}
	
	public void installBook(String bookCode){
		
		System.out.println(bookCode + "CODE");
		// String detailJSON = getBookDetailsJSON(bookCode);
		 
		 Database networkDB = new Database(Database.MY_SQL);
		 Book bookNetworkModel = new Book(networkDB , new Book(networkDB).getBookID(bookCode));
		
		//add details
		 BookModel bookModel = new BookModel(new Database());
		Long bookID = bookModel.addBook(bookNetworkModel.getBookDetails());
		
		//add courses
		if(bookID > 0){
			CourseModel courseModel = new CourseModel(new Database());
			bookModel.addBookCourses(bookID, courseModel.getIDsOfCode(bookNetworkModel.getBookCourses()));
		
		//add plugins
			PluginModel pluginModel = new PluginModel(new Database());
			bookModel.addBookPlugins(bookID, pluginModel.getIDsOfAbsoluteIDs(bookNetworkModel.getBookPlugins()));
		 
			if(!hasError){
				bookInstalled = true;
			}
		}
	}

}
