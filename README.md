# WDM_BrowserLogs
This sample project is intended to create browser logs for the executed script.
For simplicity have written small script to run on Firefox and Chrome driver and used gradle as a build tool.

# Issue
Currently, For the local exection with:
driver = new FirefoxDriver(....); 
The driver is able to run the script and create a log file under target/deviceLogs directory with the logs properly mentioned into it.

But,
When the script is ran on docker container with:
driver = new RemoteWebDriver(....);
The driver is able to run the script but the log file under target/deviceLogs directory is not getting created and populated.
This behaviour is being observed on browsers like firefox, chrome, safari

# File and Methods description

1. docker-compose.yml file having specified service which needs to be run on docker
2. browser_config.json file having firefox browser configuration 
3. Setup class setting up driver manager, setting log file and getting firefox options
4. BrowserLogDemo class having script written into it
5. Target/deviceLogs directory, here log file gets created with logs into it

# Project Setup

1. Clone the project to your local workspace
2. To pull the image and run the container on docker Run the command on terminal docker-compose up
3. In BrowserLogDemo class on line 28,  If want to execute script on docker make boolean variable executeOnDocker to true  if want to execute script on local make  boolean variable executeOnDocker to false 
4. Delete if any log file is present in deviceLogs directory
5. Run the script 

* Project run on intel chip for mac 

# Run the Script

Use command on terminal to run the particular test

for firefox test:

./gradlew clean test --tests "BrowserLogDemo.browserLogOnFirefox"

for chrome test:

./gradlew clean test --tests "BrowserLogDemo.browserLogOnChrome"

# Observation
After executing the script, log file name firefox.log file gets created at deviceLogs directory when ran on local 
But file does not get created when ran on docker

Need Solution on it to resolve this issue
