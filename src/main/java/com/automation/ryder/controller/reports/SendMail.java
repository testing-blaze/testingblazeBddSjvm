package com.automation.ryder.controller.reports;

import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.library.core.Properties_Logs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SendMail {
	
	static EnvironmentFactory reader = new EnvironmentFactory();

	public static  Boolean sendMailflag=EnvironmentFactory.getSendMail();
	public static String testReportFileName;

	//public static String passTemplate="<tr style=\\'height:15.0pt\\'><td width=64 style=\\'width:48.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$SerialNum<o:p></o:p></span></p></td><td width=149 style=\\'width:112.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#C6E0B4;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p><span style=\\'color:black\\'>$featurename<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$totscenario<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$pass<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$fail<o:p></o:p></span></p></td></tr>";
	public static String passTemplate="<tr><td style=\\'width:48.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$SerialNum</p></td><td style=\\'width:112.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#C6E0B4;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p>$featurename</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$totscenario</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$pass</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$fail</p></td></tr>";
	//public static String failTemplate="<tr style=\\'height:15.0pt\\'><td width=64 style=\\'width:48.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$SerialNum<o:p></o:p></span></p></td><td width=149 style=\\'width:112.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#F8CBAD;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p><span style=\\'color:black\\'>$featurename<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$totscenario<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$pass<o:p></o:p></span></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;background:#F8CBAD;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><span style=\\'color:black\\'>$fail<o:p></o:p></span></p></td></tr>";
	public static String failTemplate="<tr><td style=\\'width:48.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$SerialNum</p></td><td style=\\'width:112.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#F8CBAD;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p>$featurename</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$totscenario</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$pass</p></td><td style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;background:#F8CBAD;border-right:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p style=\\'text-align:center\\'>$fail</p></td></tr>";
	public static String totTemplate="<tr><td width=213 colspan=2 style=\\'width:160.0pt;border:solid windowtext 1.0pt;border-top:none;background:#7F7F7F;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><b><span style=\\'color:white\\'>Total</span></b></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#7F7F7F;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><b><span style=\\'color:white\\'>$totscenario</span></b></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#7F7F7F;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><b><span style=\\'color:white\\'>$totpass</span></b></p></td><td width=64 style=\\'width:48.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;background:#7F7F7F;padding:0in 5.4pt 0in 5.4pt;height:15.0pt\\'><p align=center style=\\'text-align:center\\'><b><span style=\\'color:white\\'>$totfail</span></b></p></td></tr>";
	public static String updatedTemplate="";


	public static void sendReport() throws  IOException {

		UploadReportTOSharePointHelper uploadreport = new UploadReportTOSharePointHelper();
		testReportFileName=uploadreport.uploadReportToSharePoint();
		testReportFileName=testReportFileName.trim().replaceAll("##-#", "").replaceAll(" ", "%20");


		String stringifiedHTML = "";

		//Configure the path of the report which automatically generated by framework (Can be .pdf, .html, .xlsx...etc )
		String FilePath = System.getProperty("user.dir")+"\\Automation_Report\\Reports\\index.html";


		try {
			stringifiedHTML = new String(Files.readAllBytes(Paths.get(FilePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String encodedString =
				Base64.getEncoder().encodeToString(stringifiedHTML.getBytes());

//		 byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//		 String decodedString = new String(decodedBytes);
//
//		 System.out.println(decodedString);

		String stringifiedJSON = "";

		//Configure the path of the JSON file to make POST call to CommsHub
		String FilePath1 = System.getProperty("user.dir")+"\\src\\test\\resources\\jsonfile\\QAReportsShare.json";


		try {
			stringifiedJSON = new String(Files.readAllBytes(Paths.get(FilePath1)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		stringifiedJSON=stringifiedJSON.replace("\"$emailList\"", getEmailList());


		String[] ar1 = stringifiedJSON.split("ReportContentAttachment");

		stringifiedJSON=stringifiedJSON.replace("$placeholder", generateHTMLTemplate());
		System.out.println("After replacing placeholder");

		stringifiedJSON=stringifiedJSON.replace("$testreportfile", testReportFileName);
		System.out.println("After replacing testreportfile");
		//	System.out.println("stringifiedJSON is ==>"+stringifiedJSON);

		//	String body = ar1[0]+encodedString+ar1[1];
		String body =stringifiedJSON;
		System.out.println("body is =>"+body);

		String uri ="https://apiqa.ryder.com/communicationhub/notifications/v1/customer";


		RestAssured.useRelaxedHTTPSValidation();
		System.out.println("after relaxed");

		Response rsp;

		// This Subscription Key is shared one for all QA Users
		rsp = RestAssured.given().headers("Ocp-Apim-Subscription-Key", "fb87f8ea1aca40e69b872981f5c29d9a").
				headers("Content-Type", "application/json").body(body).when().post(uri);
		System.out.println("before response code");
		System.out.println("Response code ==>"+rsp.getStatusCode());

		System.out.println(
				rsp.getBody().prettyPrint().toString());
		//		int code = rsp.getStatusCode();


		//System.out.println(code);

	}

	public static String getEmailList() throws IOException {

		List<String> s = new ArrayList<>();
		String listEMail=getEMailList();
		String[] emailarr=listEMail.split(",");
		s=Arrays.asList(emailarr);
		String tempstr1 = StringUtils.join(s, "\", \"");// Join with ", "
		String emailList = StringUtils.wrap(tempstr1, "\"");// Wrap step1 with "

		//System.out.println("Emaillist is =>"+emailList);

		return emailList;


	}

	public static String generateHTMLTemplate() {

		int serialnum=1;

		for(Map.Entry<String,ArrayList<Integer>> m : TestMetrics.featureMetrics.entrySet()){
			//System.out.println(m.getKey()+" "+m.getValue());
			String featureName=m.getKey();
			int totscenario=m.getValue().get(0)  ;
			int pass=m.getValue().get(1)  ;
			int fail=m.getValue().get(2)  ;

			if (fail>0) {


				updatedTemplate=updatedTemplate+(failTemplate.replace("$featurename", featureName)
						.replace("$totscenario", String.valueOf(totscenario))
						.replace("$pass", String.valueOf(pass))
						.replace("$fail", String.valueOf(fail))
						.replace("$SerialNum", String.valueOf(serialnum))
						.replace("\'", "\\'"));
			}else {

				updatedTemplate=updatedTemplate+(passTemplate.replace("$featurename", featureName)
						.replace("$totscenario", String.valueOf(totscenario))
						.replace("$pass", String.valueOf(pass))
						.replace("$fail", String.valueOf(fail))
						.replace("$SerialNum", String.valueOf(serialnum))
						.replace("\'", "\\'"));
			}

			serialnum=serialnum+1;
		}
		updatedTemplate=updatedTemplate+(totTemplate.replace("$totscenario", String.valueOf(TestMetrics.totsuitscenario))
				.replace("$totpass", String.valueOf(TestMetrics.totsuitpass))
				.replace("$totfail", String.valueOf(TestMetrics.totsuitfail))
				.replace("\'", "\\'"));

		System.out.println("updated template is ==>"+updatedTemplate);

		return updatedTemplate;

	}
	private static String getEMailList() throws IOException {
		String getemailList = "";
		getemailList = System.getProperty("emailrecepient");

		if (getemailList == null || getemailList.length() == 0) {
			getemailList = new Properties_Logs().ReadPropertyFile("config.properties","EmailRecipient");
		}
		return getemailList;
	}

	private Properties OR;
	private String ReadPropertyFile(String fileName, String parameter) throws IOException {
		OR = new Properties();
		try {
			OR.load(new InputStreamReader(getClass().getResourceAsStream("/" + fileName), StandardCharsets.UTF_8));
		} catch (Exception e) {
			OR.load(new InputStreamReader(getClass().getResourceAsStream("/configCore/" + fileName), StandardCharsets.UTF_8));
		}
		return OR.getProperty(parameter);
	}

}
