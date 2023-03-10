package com.automation.ryder.controller.reports;

import io.cucumber.java.Scenario;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMetrics {
	
	
	private static  ArrayList<Integer> testStatus;
	public static HashMap<String,ArrayList<Integer>> featureMetrics; 
	private static String currfeatureName;
	private static String prevfeatureName ="initial";
	private static int totscenario=0,pass=0,fail=0;
	public static int totsuitscenario=0,totsuitpass=0,totsuitfail=0;
	
	
	
	
	public static void captureTestMetrics(Scenario scenario) {
		
		 currfeatureName=getFeatureName(scenario);
			
		 testStatus=new ArrayList<Integer>();
		 if(prevfeatureName.equals("initial")) {
			 featureMetrics=new HashMap<String,ArrayList<Integer>>();
			 
			 prevfeatureName= currfeatureName;
		 }
		 
		 
		 //testStatus=new ArrayList<String>();
		 //totscenario,pass,fail=0
		 if (currfeatureName.equals(prevfeatureName)) {
			 
					 totscenario=totscenario+1;
					 totsuitscenario=totsuitscenario+1;
					 if(scenario.isFailed()){
						 fail=fail+1;
						 totsuitfail=totsuitfail+1;
						 
					 }else {
						 pass=pass+1;
						 totsuitpass=totsuitpass+1;
					 }
					 
					 testStatus.add(totscenario);
					 testStatus.add(pass);
					 testStatus.add(fail);
					 featureMetrics.put(currfeatureName, testStatus);
		 }else {
			 
			         totsuitscenario=totsuitscenario+1;
			 		 totscenario=0;
					 pass=0;
					 fail=0;
					 totscenario=totscenario+1;
					 
					 if(scenario.isFailed()){
						 fail=fail+1;
						 totsuitfail=totsuitfail+1;
						 
					 }else {
						 pass=pass+1;
						 totsuitpass=totsuitpass+1;
					 }
					 
					 testStatus.add(totscenario);
					 testStatus.add(pass);
					 testStatus.add(fail);
					 featureMetrics.put(currfeatureName, testStatus);
					 prevfeatureName= currfeatureName;
					 
		 }
		
		//testStatus
		//featureMetrics
	}
	
	public static String getFeatureName(Scenario sc) {
		System.out.println("====  "+sc.getName());
		String featuretemp[]=sc.getId().split("/");
		String fname=featuretemp[1].split(":")[0].split("\\.")[0];
		
		return fname;
		
	}

}
