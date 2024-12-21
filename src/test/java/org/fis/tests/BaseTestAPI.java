package org.fis.tests;

import io.restassured.RestAssured;
import org.fis.config.ConfigurationReader;
import org.testng.annotations.BeforeClass;

public class BaseTestAPI {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.getProperty("baseURI");
    }
}
