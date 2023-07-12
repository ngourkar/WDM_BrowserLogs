package example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

public class Setup {

    private static WebDriverManager driverManager;

    public static void setupWebDriverManager() {
        DriverManagerType driverManagerType = DriverManagerType.valueOf("FIREFOX");
        driverManager = WebDriverManager.getInstance(driverManagerType);
        driverManager.setup();
    }

    public static void setLogFileName(String browserType) {
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

    public static FirefoxOptions getFirefoxOptions() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("browser_config.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
