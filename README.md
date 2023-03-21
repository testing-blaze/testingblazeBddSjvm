#### Project Architecture And Work Protocol

Disclaimer: The framework piece of the project will keep getting updates gradually.
##### Code Review Protocol:
Please ensure to create a Peer review and add Automation Lead as final reviewer for merge code.

#### Installation
- Java 11
- Maven
- Appium Server (For mobile testing only)
##### src/main Section:
1- This section is dedicated for framework based libraries and configuration controllers only.</br>
2- No test specific methods and classes shall be created in it.</br>
3- If you create a new library or configuration method which is not currently available in framework,
add it to the relevant class only.</br>
4- Do not create any local methods in your test classes if that functioanlity is already available in framework. </br>
##### src/test Section:
1- This section is dedicated for all testing related activity including classes,methods,features etc. </br>
2- This shall contain a step definition file and a corresponding pageObject or Workflow class. </br>
3- The step definition shall be clean and shall not have method logics in it rather in relevant PO/WF classes.

#### Using DI for object management
We will be using DI approach for Object creation and management. Please see the sample step definition class in project.

#### Highlighting of Elements under action
Any elements which get interacted from the automation suite will be highlighted to provide better visibility.
#### How to access libraries of framework?
###### Declare a Constructor as below to access all framework capabilities:
UI Test Suite / UI+API Test Suites </br>

public YourClassName (UserPerforms userPerforms,ServiceUserPerforms serviceUserPerforms) { </br>
this.userPerforms=userPerforms </br>
this.serviceUserPerforms=serviceUserPerforms </br>
} <br>

Note: For API only testing, you can only use ServiceUserPerforms.</br>
public YourClassName (ServiceUserPerforms serviceUserPerforms) { </br>
this.serviceUserPerforms=serviceUserPerforms </br>
}

Now:</br>
1- To access UI action libraries: userPerforms.[You will see list of available libraries] </br>
2- To access backend libraries: ServiceUserPerforms.[You will see list of backend actions libraries] </br>
##### ----- Do not create any objects to access framework. </br>


##### How to run tests?
Run from Terminal/Pipeline: Use maven command as best practice </br>
Standard Command: mvn clean test -D"cucumber.filter.tags=@test1 or @test2" </br>

1- On Local Browser and API: </br>
Standard Command +
Choose Browser:
-Ddevice=chrome/firefox/ie/safari </br>

API Only Testing Suites
-Ddevice=api (No browser will be opened) || This is for only API testing projects

2- On Jenkins/Any Engine:
Standard Command +
Choose Browser:
-Ddevice=chrome/firefox/ie/safari

3- On Cloud (SauceLab/BrowserStack):
Standard Command +
Choose Browser:
-Ddevice=chrome/firefox/ie/safari
-Dhub=https://:@ondemand.saucelabs.com:443

4- On Mobile: [Appium Server will be started and stopped automatically]
Standard Command + </br>
-Dhub=http://IP_For_Appium:Port [http://0.0.00:4723] </br>
-DdeviceName= Samsung / Iphone 11 (Mobile Name) Default:Samsun/iPhone 8 Plus -Dversion= 11.0/9.0 (Android or IOS Versions) Default:8.0/11.4 For Browser: </br>
-Ddevice=android/Ios (Default: Chrome / safari) </br>
For App: </br>
-Ddevice=android </br>
-DappName=appname </br>
Note: Place the mobile app in “mobileApp” folder in project root. Add “appConfig.properties” in same folder with information of appPackage and appActivity for Android.
###### optional parameters
- Browser: -Ddevice=firefox (default is "No Browser opens") </br>
- Browser Mode: -DbrowserMode=incognito
- Environment: -Denv=UAT (default is "QA") </br>
- Parallel: -Ddataproviderthreadcount=2 (If not passed, one thread is default)
- Wait Time: -DwaitTime= 20 (Default wait time is 10 seconds. This dynamic wait)
- Headless Mode: -headless=true/false
- Send Email After Run: -DsendEmail=yes
###### Run from IDE
Go to RyderTestRunner class in src/test and put your tag to run.