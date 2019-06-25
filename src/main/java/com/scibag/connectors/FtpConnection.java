package com.scibag.connectors;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpConnection {
	
	
	public static final String HOST = "localhost";
	public static final int PORT = 21;
	public static final String USER = "scibag";
	public static final String PASSWORD = "tested";
	
	FTPClient ftpClient;
	
	public FtpConnection(){
		
		connect();
	}
	
	
	public void connect(){
		
		 this.ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(HOST, PORT);
	            showServerReply(ftpClient);
	            int replyCode = ftpClient.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) {
	                System.out.println("Operation failed. Server reply code: " + replyCode);
	                return;
	            }
	            boolean success = ftpClient.login(USER, PASSWORD);
	            showServerReply(ftpClient);
	            if (!success) {
	                System.out.println("Could not login to the server");
	                return;
	            } else {
	                System.out.println("LOGGED IN SERVER");
	            }
	        } catch (IOException ex) {
	            System.out.println("Oops! Something wrong happened");
	            ex.printStackTrace();
	        }
	        
	        
	        ftpClient.enterLocalPassiveMode();
	}

	
	
	public boolean uploadFile(String localPath, String remotePath){
		
		File firstLocalFile = new File(localPath);

		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			InputStream inputStream = new FileInputStream(firstLocalFile);
			
			System.out.println("Start uploading first file");
			boolean done;
			
				done = uploadFileStream(inputStream, remotePath);
			
			inputStream.close();
			if (done) {
				System.out.println("The first file is uploaded successfully.");
				return true;
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return false;
		
	}
	
	public boolean uploadFileStream(InputStream fileStream , String remotePath){
		
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(ftpClient.storeFile(remotePath, fileStream)){
				return true;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error storing file");
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public void downloadFile(String localPath, String remotePath){
		
		String remoteFile1 = remotePath;
		try {
			File downloadFile1 = new File(localPath);
			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
			boolean success;
			
				success = ftpClient.retrieveFile(remoteFile1, outputStream1);
			
			outputStream1.close();
	
			if (success) {
				System.out.println("File #1 has been downloaded successfully.");
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public InputStream getFileAsStream(String remotePath){
		
		try {
			String remoteFile2 = remotePath;
			return ftpClient.retrieveFileStream(remoteFile2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		return null;
	}
	
	private void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}
