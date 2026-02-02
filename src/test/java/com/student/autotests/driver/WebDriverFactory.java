package com.student.autotests.driver;

import com.student.autotests.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = TestConfig.browser().toLowerCase();
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptions());
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(edgeOptions());
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(chromeOptions());
        }
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
        return options;
    }
}
