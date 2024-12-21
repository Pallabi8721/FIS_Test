package org.fis.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class CoinDeskAPI {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.coindesk.com/v1/bpi/currentprice.json";
    }

    @Test(priority = 1, description = "Verify API returns status code 200")
    public void testStatusCode() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test(priority = 2, description = "Verify the response contains 3 BPIs: USD, GBP, and EUR")
    public void testResponseContainsCurrencies() {
        Response response = given()
                .when()
                .get()
                .then()
                .extract().response();

        // Verify the response contains USD, GBP, and EUR
        response.then().body("bpi.keySet()", hasItems("USD", "GBP", "EUR"));
        System.out.println("Response 3 BPIs: " + response.prettyPrint());
    }

    @Test(priority = 3, description = "Verify GBP description is 'British Pound Sterling'")
    public void testGBPDescription() {
        Response response = given()
                .when()
                .get()
                .then()
                .extract().response();

        // Extract GBP description
        String gbpDescription = response.path("bpi.GBP.description");
        System.out.println("Response GBP: " + response.prettyPrint());

        // Assert the GBP description
        Assert.assertEquals(gbpDescription, "British Pound Sterling", "GBP description does not match!");
    }
}
