package org.fis.utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    public static String extractValue(Response response, String jsonPth){
        return response.path(jsonPth);
    }

    public static void printPrettyJson(Response response){
        log.info("Response: {}", response.prettyPrint());
    }
}
