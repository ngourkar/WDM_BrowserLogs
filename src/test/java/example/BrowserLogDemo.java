package example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static org.assertj.core.api.Assertions.assertThat;

public class BrowserLogDemo {

    WebDriver driver;
    WebDriverManager driverManager;
    boolean executeOnDocker = false;

    @BeforeEach
    void setupTest() {
        DriverManagerType driverManagerType = DriverManagerType.valueOf("FIREFOX");
        driverManager = WebDriverManager.getInstance(driverManagerType);
        driverManager.setup();
    }

    @Test
    void browserLogOnFirefox() {
        String remoteUrl = "http://localhost:4444/wd/hub";
        setLogFileName("firefox");

        if (executeOnDocker) {
            //Running script on docker container
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), getFirefoxOptions());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //Running script locally
            driver = new FirefoxDriver(getFirefoxOptions());
        }
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        System.out.println("Script started running");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    @Test
    void browserLogOnChrome() {
        String remoteUrl = "http://localhost:4444/wd/hub";
        setLogFileName("chrome");

        if (executeOnDocker) {
            //Running script on docker container
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), getChromeOptions());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //Running script locally
            driver = new ChromeDriver(getChromeOptions());
        }
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        System.out.println("Script started running");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    private void setLogFileName(String browserType) {
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
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        firefoxOptions.addArguments("disable-infobars");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--disable-notifications");

        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        firefoxOptions.setCapability("moz:firefoxOptions",logPrefs);

        firefoxOptions.setHeadless(false);
        firefoxOptions.setAcceptInsecureCerts(true);

        System.out.println((String.format("FirefoxOptions: %s", firefoxOptions.asMap())));
        return firefoxOptions;
    }

    private ChromeOptions getChromeOptions() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-setuid-sandbox");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setHeadless(false);
        chromeOptions.setAcceptInsecureCerts(true);

        System.out.println((String.format("ChromeOptions: %s", chromeOptions.asMap())));
        return chromeOptions;
    }
}

