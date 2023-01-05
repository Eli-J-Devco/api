/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.batchjob;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import com.nwm.api.controllers.ReportsController;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelSolarOpenWeatherEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import java.io.File;

public class BatchJobFTP {
	protected final FLLogger log = FLLogger.getLogger("batchjob/" + this.getClass().getSimpleName());
	
	public static boolean downloadSingleFile(FTPClient ftpClient,
	        String remoteFilePath, String savePath) throws IOException {
	    File downloadFile = new File(savePath);
	     
	    File parentDir = downloadFile.getParentFile();
	    if (!parentDir.exists()) {
	        parentDir.mkdir();
	    }
	         
	    OutputStream outputStream = new BufferedOutputStream(
	            new FileOutputStream(downloadFile));
	    try {
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        return ftpClient.retrieveFile(remoteFilePath, outputStream);
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (outputStream != null) {
	            outputStream.close();
	        }
	    }
	}
	
	static void listDirectory(FTPClient ftpClient, String parentDir,
        String currentDir, int level) throws IOException {
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }
        FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".")
                        || currentFileName.equals("..")) {
                    // skip parent directory and directory itself
                    continue;
                }
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                if (aFile.isDirectory()) {
                    System.out.println("[" + currentFileName + "]");
                    listDirectory(ftpClient, dirToList, currentFileName, level + 1);
                } else {
                	
//                	String filePath = parentDir + "/"+ currentFileName;
                	String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
                	System.out.println("filePath: " + filePath);
                	
//                	boolean success = downloadSingleFile(ftpClient, filePath,"/Volumes/Data/sources/nextwavemonitoring/api/uploads/ftp/"+ currentFileName);
//                    if (success) {
////                        System.out.println("DOWNLOADED the file: " + filePath);
//                    } else {
////                        System.out.println("COULD NOT download the file: "+ filePath);
//                    }
                    
//                    System.out.println("File Name: "+ currentFileName);
//                    String FILENAME = "/public_html/"+currentFileName;
//                    downloadSingleFile(ftpClient, "/aaa", "aa");
                    // Instantiate the Factory
//                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                    try {
//
//                        // optional, but recommended
//                        // process XML securely, avoid attacks like XML External Entities (XXE)
//                        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//
//                        // parse XML file
//                        DocumentBuilder db = dbf.newDocumentBuilder();
//
//                        Document doc = db.parse(new File(FILENAME));
//
//                        // optional, but recommended
//                        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//                        doc.getDocumentElement().normalize();
//
//                        System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//                        System.out.println("------");
//
//                        // get <staff>
//                        NodeList list = doc.getElementsByTagName("uuid");

//                        for (int temp = 0; temp < list.getLength(); temp++) {
//
//                            Node node = list.item(temp);
//
//                            if (node.getNodeType() == Node.ELEMENT_NODE) {

//                                Element element = (Element) node;

                                // get staff's attribute
//                                String id = element.getAttribute("id");
//
//                                // get text
//                                String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
//                                String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
//                                String nickname = element.getElementsByTagName("nickname").item(0).getTextContent();
//
//                                NodeList salaryNodeList = element.getElementsByTagName("salary");
//                                String salary = salaryNodeList.item(0).getTextContent();
//
//                                // get salary's attribute
//                                String currency = salaryNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();
//
//                                System.out.println("Current Element :" + node.getNodeName());
//                                System.out.println("Staff Id : " + id);
//                                System.out.println("First Name : " + firstname);
//                                System.out.println("Last Name : " + lastname);
//                                System.out.println("Nick Name : " + nickname);
//                                System.out.printf("Salary [Currency] : %,.2f [%s]%n%n", Float.parseFloat(salary), currency);

//                            }
//                        }

//                    } catch (ParserConfigurationException | SAXException | IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }
	
	
	public void readFolderFTP() {
		try {
	        String server = "files.000webhost.com";
	        String user = "testing1692";
	        String pass = "@Khoinguyen123";
	        int port = 21;
	        String remoteDirPath = "/"; 
	        String saveDirPath = "/Volumes/Data/sources/nextwavemonitoring/api/uploads/ftp/";
	        
	        FTPClient ftpClient = new FTPClient();
	        
	        try {
	            ftpClient.connect(server, port);
	            int replyCode = ftpClient.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) {
	                System.out.println("Connect failed");
	                return;
	            }
	            boolean success = ftpClient.login(user, pass);
	            if (!success) {
	                System.out.println("Could not login to the server");
	                return;
	            }
//	            listDirectory(ftpClient, dirToList, "", 0);
	            downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath);
	            
	        } catch (IOException ex) {
	            System.out.println("Oops! Something wrong happened");
	            ex.printStackTrace();
	        } finally {
	            // logs out and disconnects from server
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
		} catch (Exception e) {
			log.error(e);
		}

	}

	
	/**
	 * Download a whole directory from a FTP server.
	 * @param ftpClient an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param parentDir Path of the parent directory of the current directory being
	 * downloaded.
	 * @param currentDir Path of the current directory being downloaded.
	 * @param saveDir path of directory where the whole remote directory will be
	 * downloaded and saved.
	 * @throws IOException if any network or IO error occurred.
	 */
	public static void downloadDirectory(FTPClient ftpClient, String parentDir,
	        String currentDir, String saveDir) throws IOException {
	    String dirToList = parentDir;
	    if (!currentDir.equals("")) {
	        dirToList += "/" + currentDir;
	    }
	 
	    FTPFile[] subFiles = ftpClient.listFiles(dirToList);
	 
	    if (subFiles != null && subFiles.length > 0) {
	        for (FTPFile aFile : subFiles) {
	            String currentFileName = aFile.getName();
	            if (currentFileName.equals(".") || currentFileName.equals("..")) {
	                // skip parent directory and the directory itself
	                continue;
	            }
	            String filePath = parentDir + "/" + currentDir + "/"
	                    + currentFileName;
	            if (currentDir.equals("")) {
	                filePath = parentDir + "/" + currentFileName;
	            }
	 
	            String newDirPath = saveDir + parentDir + File.separator
	                    + currentDir + File.separator + currentFileName;
	            if (currentDir.equals("")) {
	                newDirPath = saveDir + parentDir + File.separator
	                          + currentFileName;
	            }
	 
	            if (aFile.isDirectory()) {
	                // create the directory in saveDir
	                File newDir = new File(newDirPath);
	                boolean created = newDir.mkdirs();
	                if (created) {
	                    System.out.println("CREATED the directory: " + newDirPath);
	                } else {
	                    System.out.println("COULD NOT create the directory: " + newDirPath);
	                }
	 
	                // download the sub directory
	                downloadDirectory(ftpClient, dirToList, currentFileName, saveDir);
	            } else {
	                // download the file
	            	File f = new File(newDirPath);
	            	if(!f.exists()) { 
	            	    // do something
	            		boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
	            		if (success) {
		                    System.out.println("1DOWNLOADED the file: " + filePath);
		                } else {
		                    System.out.println("1COULD NOT download the file: " + filePath);
		                }
	            	}
	                
	                
	            }
	        }
	    }
	}
	
	
}
