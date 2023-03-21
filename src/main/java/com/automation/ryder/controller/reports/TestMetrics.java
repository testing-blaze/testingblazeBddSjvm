package com.automation.ryder.controller.reports;

import io.cucumber.java.Scenario;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMetrics {


    private static volatile ArrayList<Integer> testStatus;
    public static volatile HashMap<String, ArrayList<Integer>> featureMetrics;
    private static volatile String currfeatureName;
    private static volatile String prevfeatureName = "initial";
    private static volatile int totscenario = 0, pass = 0, fail = 0;
    public static volatile int totsuitscenario = 0, totsuitpass = 0, totsuitfail = 0;


    public synchronized static void captureTestMetrics(Scenario scenario) {

        currfeatureName = getFeatureName(scenario);

        testStatus = new ArrayList<Integer>();
        if (prevfeatureName.equals("initial")) {
            featureMetrics = new HashMap<String, ArrayList<Integer>>();

            prevfeatureName = currfeatureName;
        }


        //testStatus=new ArrayList<String>();
        //totscenario,pass,fail=0
        if (currfeatureName.equals(prevfeatureName)) {

            totscenario = totscenario + 1;
            totsuitscenario = totsuitscenario + 1;
            if (scenario.isFailed()) {
                fail = fail + 1;
                totsuitfail = totsuitfail + 1;

            } else {
                pass = pass + 1;
                totsuitpass = totsuitpass + 1;
            }

            testStatus.add(totscenario);
            testStatus.add(pass);
            testStatus.add(fail);
            featureMetrics.put(currfeatureName, testStatus);
        } else {

            totsuitscenario = totsuitscenario + 1;
            totscenario = 0;
            pass = 0;
            fail = 0;
            totscenario = totscenario + 1;

            if (scenario.isFailed()) {
                fail = fail + 1;
                totsuitfail = totsuitfail + 1;

            } else {
                pass = pass + 1;
                totsuitpass = totsuitpass + 1;
            }

            testStatus.add(totscenario);
            testStatus.add(pass);
            testStatus.add(fail);
            featureMetrics.put(currfeatureName, testStatus);
            prevfeatureName = currfeatureName;

        }

    }

    private synchronized static String getFeatureName(Scenario sc) {
        return sc.getId().split("/")[2].split("\\.")[0];
    }

}
