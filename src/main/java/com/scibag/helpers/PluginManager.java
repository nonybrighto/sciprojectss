package com.scibag.helpers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scibag.api.SciBagPlugin;
import com.scibag.connectors.Database;
import com.scibag.models.BookModel;
import com.scibag.models.CourseModel;
import com.scibag.models.PluginModel;

public class PluginManager {
	
	ServiceLoader<SciBagPlugin> serviceLoader;
	
	boolean pluginInstalled;
	
	String installPath;
	
	InstallType installType;
	
	public static enum InstallType{TEMP, MAIN};
	
	public PluginManager(){
		
		this(InstallType.MAIN);
	}
	
	public PluginManager(InstallType type){
		

		AppProperties prop = new AppProperties(AppProperties.CONF_FILE);
		
		this.installType = type;
		String folder = "";
		if(type == InstallType.MAIN){
			folder = "plugins";
		}else{
			folder = "temp";
		}
		
		installPath = prop.getProperty("app_home")+File.separator+folder;
		
		System.out.println(installPath);
		File file = new File(installPath);
		
		//System.out.println(prop.getProperty("app_home")+File.separator+"plugins -------");
		File[] flist = file.listFiles(new FileFilter() {
            public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
        });
        URL[] urls = new URL[flist.length];
        for (int i = 0; i < flist.length; i++)
			try {
				urls[i] = flist[i].toURI().toURL();
				System.out.println("got "+i);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        URLClassLoader ucl = new URLClassLoader(urls);
        serviceLoader = ServiceLoader.load(SciBagPlugin.class, ucl);
	}
	
	
	
	public boolean isPluginInstalled(){
		return this.pluginInstalled;
	}
	
	
	public SciBagPlugin getPluginInstance(String absoluteID){
		
		Iterator<SciBagPlugin> apit = serviceLoader.iterator();
        while (apit.hasNext()){
        	SciBagPlugin plugin = apit.next();
        	System.out.println("got plugin named "+plugin.getName());
        	if(plugin.getAbsoluteID().equals(absoluteID)){
        		return plugin;
        	}
        }
        
        return null;
        
	}

	
	public String downloadPlugin(String URLString){
		
		File downloadedPlugin = null;
		try {
			URL url = new URL(URLString);
			
			String fileName = URLString.substring(URLString.lastIndexOf('/'));
			System.out.println(fileName);
			AppProperties prop = new AppProperties(AppProperties.CONF_FILE);
			FileUtils.copyURLToFile(url, 
					downloadedPlugin = new File(prop.getProperty("app_home")+File.separator+"downloads"+File.separator+fileName));
		}  catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
			
		}
		
		return downloadedPlugin.getAbsolutePath();
	}
	
	
	public String getPluginDetail(String jarPath, String detail) throws IOException{
		
		URL url;
		InputStream is = null;
		
		url = new URL("jar:file:/"+jarPath+"!/details.inf");
		is = url.openStream();
		
		String detailGotten = "";
		Scanner scanner = new Scanner(is);
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine();
			System.out.println(line);
			String[] lineSplit = line.split("\t");
			
			if(lineSplit[0].equals(detail)){
				detailGotten = lineSplit[1];
				scanner.close();
				return detailGotten;
			}
		}
		
		scanner.close();
		return null;
	}
	
	
	public InputStream getPluginIconAsStream(String jarPath) throws IOException{
		
		URL url = new URL("jar:file:/"+jarPath+"!/icon.png");
		return url.openStream();
	}
	
	public InputStream getPluginNoteAsStream(String jarPath) throws IOException{
		
		URL url = new URL("jar:file:/"+jarPath+"!/note.pdf");
		return url.openStream();
	}
	
	public void installPlugin(String pluginPath){
		
		
		pluginInstalled = false;
		String pluginAbsoluteID = "";
		
		try {
			pluginAbsoluteID = getPluginDetail(pluginPath,"absoluteID");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
						pluginInstalled = false;
						System.out.println("dddddswww");
						e2.printStackTrace();
						return;
		}
		
		
		System.out.println("installing abs id is "+pluginAbsoluteID);
		
		if(!pluginAbsoluteID.isEmpty()){
			try {
				FileUtils.copyFile(new File(pluginPath), 
						new File(installPath+File.separator+pluginAbsoluteID+".jar"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if(this.installType == InstallType.MAIN){
				System.out.println("installing type is main");
				PluginModel model = new PluginModel(new Database());
				//icon 
				try {
					File file = new File(installPath+
							File.separator+"icons"+File.separator+pluginAbsoluteID+".png");
					
					FileUtils.copyToFile(getPluginIconAsStream(pluginPath), file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//note
				try {
					
					File file = new File(installPath+
							File.separator+"notes"+File.separator+pluginAbsoluteID+".pdf");
					
					FileUtils.copyToFile(getPluginNoteAsStream(pluginPath), file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				if(model.pluginExists(pluginAbsoluteID)){
					pluginInstalled = true;
					System.out.println("exists---------");
				}else{
					pluginInstalled = true;
					PluginManager postInstallManager = new PluginManager();
					SciBagPlugin installedPlugin = postInstallManager.getPluginInstance(pluginAbsoluteID);
					int difficulty = installedPlugin.getDifficulty().getValue();
					int type = installedPlugin.getType().getValue();
					
					System.out.println("difficulty "+difficulty+" type "+type);
					//add plugin
					System.out.println("NAME : "+installedPlugin.getName()+" ABS "+installedPlugin.getAbsoluteID());
					Long pluginID = model.addPlugin(installedPlugin.getName(), type , 
							installedPlugin.getDescription(), difficulty, installedPlugin.getAbsoluteID());
					
					//add plugin courses
					CourseModel courseModel = new CourseModel(new Database());
					model.addPluginCourses(pluginID, courseModel.getIDsOfCode(installedPlugin.getPluginCourses()));
					
					//add related plugins
					
					model.addPluginRelationship(pluginID, model.getIDsOfAbsoluteIDs(installedPlugin.getRelatedPlugins()));
					
					//add related books
					BookModel bookModel = new BookModel(new Database());
					model.addPluginBooks(pluginID, bookModel.getIDsOfCode(installedPlugin.getRelatedBooks()));
					
					
					installedPlugin = null;
				}
			}
			
		}else{
			pluginInstalled = false;
		}
	}

}
