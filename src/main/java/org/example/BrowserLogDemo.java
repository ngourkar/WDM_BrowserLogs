package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static org.assertj.core.api.Assertions.assertThat;


public class BrowserLogDemo {

    WebDriver driver;
    WebDriverManager driverManager;

    @BeforeEach
    void setupTest() {
        DriverManagerType driverManagerType = DriverManagerType.valueOf("FIREFOX");
        driverManager = WebDriverManager.getInstance(driverManagerType);
        driverManager.setup();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() {

        String remoteUrl = "http://localhost:4444/wd/hub";
        setLogFileName("firefox");

//        try {
//            driver = new RemoteWebDriver(new URL(remoteUrl), getFirefoxOptions());
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
        driver = new FirefoxDriver(getFirefoxOptions());

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        System.out.println("Script started running");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    private static void setLogFileName(String browserType) {
        String scenarioLogDir = "./target";
        browserType = browserType.toLowerCase();
        String logFile = String.format("%s%sdeviceLogs%s%s.log", scenarioLogDir, File.separator,
                File.separator, browserType);

        File file = new File(logFile);
        file.getParentFile().mkdirs();

        String logMessage = String.format("Creating %s logs in file: %s", browserType, logFile);
        System.out.println("logmessage is::" + logMessage);

        System.setProperty("webdriver." + browserType + ".logfile", logFile);

    }

    private FirefoxOptions getFirefoxOptions() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("browser_config.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // typecasting obj to JSONObject
        JSONObject firefoxConfiguration = (JSONObject) ((JSONObject) obj).get("firefox");
        System.out.println("json object is::"+firefoxConfiguration);


        boolean enableVerboseLogging = (boolean)firefoxConfiguration.get("verboseLogging");
        boolean acceptInsecureCerts = (boolean)firefoxConfiguration.get("acceptInsecureCerts");

        FirefoxOptions firefoxOptions = new FirefoxOptions();

        FirefoxProfile firefoxProfile = new FirefoxProfile();
        JSONObject profileObject = (JSONObject) firefoxConfiguration.get("firefoxProfile");
        profileObject.keySet().forEach(key -> {
            if(profileObject.get(key) instanceof Boolean) {
                firefoxProfile.setPreference((String)key, profileObject.get(key));
            } else if(profileObject.get(key) instanceof String) {
                firefoxProfile.setPreference((String)key, profileObject.get(key));
            }
        });
        firefoxOptions.setProfile(firefoxProfile);

        JSONObject preferencesObject = (JSONObject) firefoxConfiguration.get("preferences");
        preferencesObject.keySet().forEach(key -> {
            if(preferencesObject.get(key) instanceof Boolean) {
                firefoxOptions.addPreference((String)key, preferencesObject.get(key));
            } else if(preferencesObject.get(key) instanceof String) {
                firefoxOptions.addPreference((String)key, preferencesObject.get(key));
            }
        });

        JSONArray arguments = (JSONArray) firefoxConfiguration.get("arguments");
        arguments.forEach(argument -> firefoxOptions.addArguments(argument.toString()));

        LoggingPreferences logPrefs = new LoggingPreferences();
        if(enableVerboseLogging) {
            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        } else {
            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.INFO);
            logPrefs.enable(LogType.BROWSER, Level.INFO);
            logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
        }
        firefoxOptions.setCapability("moz:firefoxOptions",logPrefs);

        JSONObject headlessOptions = (JSONObject) firefoxConfiguration.get("headlessOptions");
        boolean isRunInHeadlessMode = (boolean)headlessOptions.get("headless");

        firefoxOptions.setHeadless(isRunInHeadlessMode);
        firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);

        System.out.println((String.format("FirefoxOptions: %s", firefoxOptions.asMap())));
        return firefoxOptions;
    }
}

