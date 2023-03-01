package com.automation.ryder.controller.reports;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * @author Suresh Babu
 *
 *         01/30/2K22
 *
 */

public class UploadReportTOSharePointHelper {

	//private static Logger oLog = LoggerHelper.getLogger(UploadReportTOSharePointHelper.class);
	private Properties prop = null;

	public UploadReportTOSharePointHelper() {
		prop = new Properties();

		try {
			FileInputStream in = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\resources\\configFile\\sharepoint-config.properties");
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String uploadReportToSharePoint() throws IOException, java.io.IOException {

		String SharePointUrl = getSharePointUrl();
		String SharePointLocation = getSpLocationToUploadFile();
		String UserName = getSpUserName();
		String Password = getSpPassword();

		//String now = DateTimeHelper.getCurrentDate();

		//String datetimeext = DateTimeHelper.getCurrentDateTime("ddMMyyHHmmss");

		//SharePointLocation = SharePointLocation + "/" + now;
		String path = System.getProperty("user.dir");

		String fileName = path + getResultFileLoc();

		File file = new File(fileName);

		// Create an object of the File class
		// Replace the file path with path of the directory
		String currfilebase = fileName.split("\\.(?=[^\\\\.]+$)")[0];
		String currfileext = fileName.split("\\.(?=[^\\\\.]+$)")[1];
		//String updatedfilename = currfilebase + "_" + datetimeext + "." + currfileext;
		//File rename = new File(updatedfilename);
		//Files.copy(file,rename);

		String filepath = path + "\\src\\main\\resources\\SharePointLib\\PowerShellscripts\\SharePoint.ps1";
		String dllpth = path + getDllLibPath();

		filepath = filepath.replaceAll(" ", "` ");
		//String powerCommand = "cmd /C " + "powershell " + filepath + " -URL '" + SharePointUrl + "' -DocumentLocation '"
			//	+ SharePointLocation + "' -user '" + UserName + "' -cred '" + Password + "' -path '" + updatedfilename
			//	+ "' -dllPath '" + dllpth + "'";

		Runtime runtimeCMD = Runtime.getRuntime();
		//Process ExecuteProcess = runtimeCMD.exec(powerCommand);
		//ExecuteProcess.getOutputStream().close();
		String line, sharePointURL = null;

		//BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(ExecuteProcess.getInputStream()));

		//oLog.debug("Input stream: " + bufferedreader);
		/*
		int i = 1;
		while ((line = bufferedreader.readLine()) != null) {
			System.out.println("LOG Details:" + line);
			//oLog.debug("LOG Details:" + line);
			if (line.toLowerCase().contains("FileURL".toLowerCase())) {
				String[] fileURL = line.split("FileURL:");
				sharePointURL = fileURL[1];
				//oLog.debug("SharePoint File Uploaded At:" + sharePointURL);
				System.out.println("SharePoint File Uploaded At:" + sharePointURL);
				break;
			}
			i++;
		}*/

		//bufferedreader.close();
		//System.out.println("Standard Error:");
		//BufferedReader stderr = new BufferedReader(new InputStreamReader(ExecuteProcess.getErrorStream()));

		//oLog.debug("Standard Error: " + stderr);

		//while ((line = stderr.readLine()) != null) {
			//System.out.println(line);
		//}
		//stderr.close();
		System.out.println("Done uploading the Result file at sharepoint");
		System.out.println("Share Point URL: " + sharePointURL);
		//oLog.debug("Share Point URL: " + sharePointURL);
		
		try {
		//	if(rename.delete()) {
		//		oLog.debug("Succesfully deleted the temp file")	;
		//	}
		}
		catch(Exception e){
		//	oLog.error("Unable to delete temp result file"+e);
		}
		return sharePointURL;

	}

	public String getSharePointUrl() {
		return prop.getProperty("SharePointLocation");
	}

	public String getSpLocationToUploadFile() {
		return prop.getProperty("SpLocationToUploadFile");
	}

	public String getSpUserName() {
		return prop.getProperty("SpUserName");
	}

	public String getSpPassword() {
		return prop.getProperty("SpPassword");
	}

	public String getResultFileLoc() {
		return prop.getProperty("ResultFileLoc");
	}

	public String getDllLibPath() {
		return prop.getProperty("DllLibPath");
	}
	
	
	
		

	  

}
