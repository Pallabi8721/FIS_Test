package org.fis.tests.ui;


import org.fis.locators.EbayLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EbayAddToCartTest extends BaseTestUI {

    private static final Logger log = LoggerFactory.getLogger(EbayAddToCartTest.class);

    @Test
    public void testAddToCart() {
        WebElement searchBox = driver.findElement(By.xpath(EbayLocators.SEARCH_BOX));
        searchBox.sendKeys("book");
        WebElement searchButton = driver.findElement(By.xpath(EbayLocators.SEARCH_BUTTON));
        searchButton.click();

        List<WebElement> items = driver.findElements(By.xpath(EbayLocators.FIRST_ITEM));
        if (items.isEmpty()) {
            log.info("No items found matching the search criteria.");
            return;
        }
        items.get(0).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> iterator = windows.iterator();
        iterator.next();
        driver.switchTo().window(iterator.next());

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EbayLocators.ADD_TO_CART_BUTTON))); // Replace with the correct ID
        addToCartButton.click();

        // Verify the cart message
        WebElement cartMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(EbayLocators.CART_MESSAGE)));
        Assert.assertTrue(cartMessage.isDisplayed(), "The cart message is not displayed!");
        Assert.assertEquals(cartMessage.getAttribute("aria-label"), "Your shopping cart contains 1 item", "Cart message does not match!");
        log.info("Assertion passed: 1 item successfully added to the cart!");
    }

}
