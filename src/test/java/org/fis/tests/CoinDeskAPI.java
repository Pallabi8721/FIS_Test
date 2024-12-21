package org.fis.tests;

import io.restassured.response.Response;
import org.fis.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class CoinDeskAPI extends BaseTestAPI {

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
        response.then().body("bpi.keySet()", hasItems("USD", "GBP", "EUR"));
        JsonUtils.printPrettyJson(response);
    }

    @Test(priority = 3, description = "Verify GBP description is 'British Pound Sterling'")
    public void testGBPDescription() {
        Response response = given()
                .when()
                .get()
                .then()
                .extract().response();

        String gbpDescription = JsonUtils.extractValue(response,"bpi.GBP.description");
        JsonUtils.printPrettyJson(response);

        Assert.assertEquals(gbpDescription, "British Pound Sterling", "GBP description does not match!");
    }
}
