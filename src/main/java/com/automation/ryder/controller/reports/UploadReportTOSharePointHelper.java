package com.automation.ryder.controller.reports;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
					+ "\\src\\main\\resources\\configCore\\sharepoint-config.properties");
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

		String now = getCurrentDate();

		String datetimeext = getCurrentDateTime("ddMMyyHHmmss");

		SharePointLocation = SharePointLocation + "/" + now;
		String path = System.getProperty("user.dir");

		String fileName = path + getResultFileLoc();

		File file = new File(fileName);

		 //Create an object of the File class
		 //Replace the file path with path of the directory
		String currfilebase = fileName.split("\\.(?=[^\\\\.]+$)")[0];
		String currfileext = fileName.split("\\.(?=[^\\\\.]+$)")[1];
		String updatedfilename = currfilebase + "_" + datetimeext + "." + currfileext;
		File rename = new File(updatedfilename);
		Files.copy(file.toPath(), rename.toPath());

		String filepath = path + "\\src\\test\\resources\\SharePointLib\\PowerShellscripts\\SharePoint.ps1";
		String dllpth = path + getDllLibPath();

		filepath = filepath.replaceAll(" ", "` ");
		String powerCommand = "cmd /C " + "powershell " + filepath + " -URL '" + SharePointUrl + "' -DocumentLocation '"
				+ SharePointLocation + "' -user '" + UserName + "' -cred '" + Password + "' -path '" + updatedfilename
				+ "' -dllPath '" + dllpth + "'";

		Runtime runtimeCMD = Runtime.getRuntime();
		Process ExecuteProcess = runtimeCMD.exec(powerCommand);
		ExecuteProcess.getOutputStream().close();
		String line, sharePointURL = null;

		BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(ExecuteProcess.getInputStream()));
		int i = 1;
		while ((line = bufferedreader.readLine()) != null) {
			if (line.toLowerCase().contains("FileURL".toLowerCase())) {
				String[] fileURL = line.split("FileURL:");
				sharePointURL = fileURL[1];
				//oLog.debug("SharePoint File Uploaded At:" + sharePointURL);
				System.out.println("SharePoint File Uploaded At:" + sharePointURL);
				break;
			}
			i++;
		}

		bufferedreader.close();
		BufferedReader stderr = new BufferedReader(new InputStreamReader(ExecuteProcess.getErrorStream()));
		stderr.close();
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

	private String getCurrentDate() {
		return getCurrentDateTime("_yyyy-MM-dd_HH-mm-ss").substring(1, 11);
	}
	private String getCurrentDateTime(String format) {

		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		String time = "" + dateFormat.format(cal.getTime());
		return time;
	}

}
