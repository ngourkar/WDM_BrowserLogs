package org.example;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;


import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class Main {

    WebDriver driver;
    WebDriverManager wdm = WebDriverManager.firefoxdriver().browserInDocker().watch();
//    WebDriverManager wdm = WebDriverManager.safaridriver().browserInDocker().watch();

    static final Logger log = getLogger(lookup().lookupClass());

    @BeforeEach
    void setupTest() throws MalformedURLException {
//        WebDriver wem = WebDriverManager.chromedriver().browserInDocker().proxy("").create();
//        System.out.println("the container id is::"+ id);

//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--no-sandbox");
//        chromeOptions.addArguments("--disable-dev-shm-usage");
//
//        String remoteUrl =
//                "http://localhost:" + "4444" + "/wd/hub";
//        new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
//
//
//        LoggingPreferences logPrefs = new LoggingPreferences();
//        System.setProperty("webdriver.chrome.verboseLogging", "true");
//        chromeOptions.setLogLevel(ChromeDriverLogLevel.DEBUG);
//        logPrefs.enable(LogType.BROWSER, Level.ALL);
//        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//        chromeOptions.setCapability(ChromeOptions.LOGGING_PREFS, logPrefs);
//
//        System.out.println((String.format("ChromeOptions: %s", chromeOptions.asMap())));
//
//        driver = new ChromeDriver(chromeOptions);

//        boolean enableVerboseLogging = firefoxConfiguration.getBoolean(VERBOSE_LOGGING);
//        boolean acceptInsecureCerts = firefoxConfiguration.getBoolean(ACCEPT_INSECURE_CERTS);
//        shouldBrowserBeMaximized = firefoxConfiguration.getBoolean(MAXIMIZE);
//        String proxyUrl = Runner.getProxyURL();
//
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
////        setLogFileName(forUserPersona, testExecutionContext, "Firefox");
//
//        FirefoxProfile firefoxProfile = new FirefoxProfile();
//        JSONObject profileObject = firefoxConfiguration.getJSONObject("firefoxProfile");
//        profileObject.keySet().forEach(key -> {
//            if (profileObject.get(key) instanceof Boolean) {
//                firefoxProfile.setPreference(key, profileObject.getBoolean(key));
//            } else if (profileObject.get(key) instanceof String) {
//                firefoxProfile.setPreference(key, profileObject.getString(key));
//            }
//        });
//        firefoxOptions.setProfile(firefoxProfile);
//
//        JSONObject preferencesObject = firefoxConfiguration.getJSONObject("preferences");
//        preferencesObject.keySet().forEach(key -> {
//            if (preferencesObject.get(key) instanceof Boolean) {
//                firefoxOptions.addPreference(key, preferencesObject.getBoolean(key));
//            } else if (preferencesObject.get(key) instanceof String) {
//                firefoxOptions.addPreference(key, preferencesObject.getString(key));
//            }
//        });
//
//        JSONArray arguments = firefoxConfiguration.getJSONArray("arguments");
//        arguments.forEach(argument -> firefoxOptions.addArguments(argument.toString()));
//
//        LoggingPreferences logPrefs = new LoggingPreferences();
//        if (enableVerboseLogging) {
//            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
//            logPrefs.enable(LogType.BROWSER, Level.ALL);
//            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//        } else {
//            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.INFO);
//            logPrefs.enable(LogType.BROWSER, Level.INFO);
//            logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
//        }
//        firefoxOptions.setCapability("moz:firefoxOptions", logPrefs);
//
//        if (null != proxyUrl) {
//            LOGGER.info(SETTING_PROXY_FOR_BROWSER + proxyUrl);
//            firefoxOptions.setProxy(new Proxy().setHttpProxy(proxyUrl));
//        }
//
//        JSONObject headlessOptions = firefoxConfiguration.getJSONObject("headlessOptions");
//        isRunInHeadlessMode = headlessOptions.getBoolean("headless");
//
//        firefoxOptions.setHeadless(isRunInHeadlessMode);
//        firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);
//
//        LOGGER.info(String.format("FirefoxOptions: %s", firefoxOptions.asMap()));





//        driver = wdm.capabilities().create();
//        System.out.println(wdm.getDownloadedDriverPath());
//        System.out.println(wdm.getDownloadedDriverVersion());
//        System.out.println(wdm.getDriverVersions());
//        System.out.println(wdm.getDriverManagerType());
//        System.out.println("hello browser:" + wdm.getDockerBrowserContainerId());
//        System.out.println("The logs of the browser::" + wdm.getLogs(driver));

    }

    @AfterEach
    void teardown() {
//        System.out.println("printing the logs");
//        List<Map<String, Object>> logMessages = wdm.getLogs();
//
//        assertThat(logMessages).hasSize(5);
//
//        logMessages.forEach(map -> log.debug("[{}] [{}] {}",
//                map.get("datetime"),
//                String.format("%1$-14s",
//                        map.get("source").toString().toUpperCase() + "."
//                                + map.get("type").toString().toUpperCase()),
//                map.get("message")));
        //       wdm.quit();
        //1b5797ef3781 echo "1b5797ef3781" | awk 'NR==2{print $1}'
//
//        docker exec 1b5797ef3781 touch /nayan_log.txt


    }

    @Test
    void test() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        System.out.println("heello in the script running");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");


//        String logFile = "./tmp/browserLog.log";
//
//        File file = new File(logFile);
////        file.getParentFile().mkdirs();
//        if (file.exists()) {
//            System.out.println("file is getting created" + file.getAbsolutePath());
//            System.out.println("file is getting created2" + file.getParent());
//        } else {
//            System.out.println("nhi bani");
//        }

        String container_id = "";
        String filePath = "/tmp/browserLog.log"; // Replace with the desired path and filename inside the container

        try {             // Create the ProcessBuilder with the docker ps command
            ProcessBuilder processBuilder = new ProcessBuilder("docker", "ps");
//             Redirect the error stream to the standard output
            processBuilder.redirectErrorStream(true);
//             Start the process
            Process process = processBuilder.start();
//             Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("the value of command::" + line);
                container_id = line.split(" ")[0];
            }
//             Wait for the process to complete
            int exitCode = process.waitFor();
//             Print the exit code
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


//            //prepare command to retrieve the list of (running) containers
//            ListContainersCmd listContainersCmd = dockerClient.listContainersCmd().withStatusFilter(Collections.singleton("running"));
//            //and set the generic filter regarding name
////        listContainersCmd.getFilters().put("name", Arrays.asList("redis"));
//            //finally, run the command
//            List<Container> exec = listContainersCmd.exec();
//            System.out.println("the value of containers::" + exec.get(0).toString());


        String containerId = container_id; // Replace with your container ID or name
        System.out.println("the container id is::" + containerId);


        try {             // Create the ProcessBuilder with the docker exec command
            ProcessBuilder processBuilder = new ProcessBuilder("docker", "exec", "-i", containerId, "bash", "-c", "ls ; touch /tmp/browserLog.log; pwd");
            // Start the process
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("the value of command2 is::" + line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            // Print the exit code
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Create the file using a shell command inside the container
//        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
//                .withCmd("sh", "-c", "echo 'File content' > " + filePath)
//                .exec();
//
//
//        // Start the command execution
//        try {
//            dockerClient.execStartCmd(execCreateCmdResponse.getId())
//                    .exec(new ExecStartResultCallback())
//                    .awaitCompletion();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Wait for the container to finish executing the command
//        try {
//            dockerClient.waitContainerCmd(containerId)
//                    .exec(new WaitContainerResultCallback())
//                    .awaitCompletion();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Retrieve the container logs to verify file creation
//        StringBuilder logs = new StringBuilder();
//        dockerClient.logContainerCmd(containerId)
//                .withStdOut(true)
//                .withStdErr(true)
//                .exec(new LogContainerResultCallback() {
//                    @Override
//                    public void onNext(Frame item) {
//                        logs.append(new String(item.getPayload(), StandardCharsets.UTF_8));
//                    }
//                });
//
//        System.out.println("Container logs:\n" + logs);
//

        String logMessage = String.format("Creating %s logs in file: %s", "firefox", filePath);
        System.setProperty("webdriver.firefox.logfile", filePath);
        System.out.println("logmessage is::" + logMessage);


//        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
//
//        DockerClient docker = DockerClientBuilder.getInstance(config).build();
////prepare command to retrieve the list of (running) containers
//        ListContainersCmd listContainersCmd = docker.listContainersCmd().withStatusFilter(Collections.singleton("running"));
////and set the generic filter regarding name
//        listContainersCmd.getFilters().put("name", Arrays.asList("redis"));
////finally, run the command
//        List<Container> exec = listContainersCmd.exec();
//
//        // Create and start the command in the Docker container
//        ExecCreateCmdResponse createCmdResponse = docker.execCreateCmd(exec.get(0).getId())
//                .withCmd("ls")
//                .withAttachStdout(true)
//                .exec();
//


//            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//                System.out.println("value of file is:::::");
//                String line;
//                while ((line = br.readLine()) != null) {
//                    System.out.println("items::" + line);
//                }
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
    }

}