package example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class BrowserLogDemo {

    WebDriver driver;

    @BeforeEach
    void setupTest() {
        Setup.setupWebDriverManager();
    }

    @Test
    void test() {
        boolean executeOnDocker = true;
        String remoteUrl = "http://localhost:4444/wd/hub";
        Setup.setLogFileName("firefox");

        if (executeOnDocker) {
            //Running script on docker container
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), Setup.getFirefoxOptions());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //Running script locally
            driver = new FirefoxDriver(Setup.getFirefoxOptions());
        }
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        System.out.println("Script started running");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}

