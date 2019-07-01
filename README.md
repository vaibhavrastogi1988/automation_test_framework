a# automation_test_framework
TestNG based Selenium automation framework

Steps to run tests via command line:
1. Check out the code from github
2. Install all the dependencies present in pom.xml file
3. Run  command "mvn clean"
4. Run command "mvn test" in order to run all the tests from command line

Steps to run tests via Eclipse:
1. Check out the code from github
2. Import the project in Eclipse as a Existing Maven project
3. Install all the dependencies present in pom.xml file
4. Right click on the project, select Run As-> Maven test

Structure of Automation Framework:
1. Automation Framework is Maven based framework
2. Automation Framwork consists of 2 layers namely Library layer and Test Layer
3. Library layer keeps all the low level funtions of selenium (It is generic layer which can keep all the low level function of the SUT)
4. Test layer keeps all the automated test cases
5. For selenium POM (Page Object Model) structure is followed
6. For reporting, ExtentReport is integrated which produce extent.html file at following location (target/surefire-reports/html/extent.html)
7. When any test fails then screenshot is captured and attached in ExtentReport

Essential files of the framework:
1. OR.json file keeps all the locators of the webpage (Automation_Test_Framework\src\test\resources\com\atf\properties)
2. AppSettings.json file keeps all the test data that need to be used while running tests (Automation_Test_Framework\src\test\resources\com\atf\properties)
3. testng.xml file keeps all listeners and test cases that need to be executed (Automation_Test_Framework\src\test\resources\com\atf\runner)
