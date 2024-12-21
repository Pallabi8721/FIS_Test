package org.fis.tests;


import org.fis.config.ConfigurationReader;
import org.fis.driver.DriverOperation;
import org.fis.locators.EbayLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EbayTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = DriverOperation.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAddToCart() {
        // Fetch the base URL from config.properties
        String baseUrl = ConfigurationReader.getProperty("baseUrl");
        driver.get(baseUrl);

        // Perform search for "book"
        WebElement searchBox = driver.findElement(By.xpath(EbayLocators.SEARCH_BOX));
        searchBox.sendKeys("book");
        WebElement searchButton = driver.findElement(By.xpath(EbayLocators.SEARCH_BUTTON));
        searchButton.click();

        // Step 2: Locate the first item in search results using a xpath
        // Locate the first item in search results
        List<WebElement> items = driver.findElements(By.xpath(EbayLocators.FIRST_ITEM));
        if (items.isEmpty()) {
            System.out.println("No items found matching the search criteria.");
            return;
        }
        items.get(0).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> iterator = windows.iterator();
        iterator.next();
        driver.switchTo().window(iterator.next());

        // Wait for the page to load
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EbayLocators.ADD_TO_CART_BUTTON))); // Replace with the correct ID
        addToCartButton.click();

        // Verify the cart message
        WebElement cartMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(EbayLocators.CART_MESSAGE)));
        Assert.assertTrue(cartMessage.isDisplayed(), "The cart message is not displayed!");
        Assert.assertEquals(cartMessage.getAttribute("aria-label"), "Your shopping cart contains 1 item", "Cart message does not match!");
        System.out.println("Assertion passed: 1 item successfully added to the cart!");

    }

    @AfterClass
    public void tearDown() {
        DriverOperation.quitDriver();
    }
}
