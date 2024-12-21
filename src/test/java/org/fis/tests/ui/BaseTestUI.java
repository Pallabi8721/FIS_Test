package org.fis.tests.ui;

import org.fis.config.ConfigurationReader;
import org.fis.driver.DriverOperation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTestUI {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp(){
        driver = DriverOperation.getDriver();
        String baseUrl = ConfigurationReader.getProperty("baseUrl");
        driver.get(baseUrl);

        int timeout = Integer.parseInt(ConfigurationReader.getProperty("timeout"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    @AfterClass
    public void tearDown(){
        DriverOperation.quitDriver();
    }
}
