package base;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import utils.ConfigLoader;

public class BaseTest {
    protected String token;
    protected String authCode;

    @BeforeTest
    public void authenticate() {
        RestAssured.baseURI = ConfigLoader.get("base_url");

        // Step 1: Login and get auth_code
        authCode = 
            given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", ConfigLoader.get("username"))
                .formParam("password", ConfigLoader.get("password"))
            .when()
                .post(ConfigLoader.get("login_endpoint"))
            .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("auth_code");

        // Step 2: Exchange auth_code for access_token
        token = 
            given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("auth_code", authCode)
            .when()
                .post(ConfigLoader.get("token_endpoint"))
            .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("access_token");

        System.out.println("======Authentication successful. Token obtained.======");
    }

    public String getAuthToken() {
        return token;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getEndpoint(String endpointKey) {
        return ConfigLoader.get("base_url") + ConfigLoader.get(endpointKey);
    }

    public String getEndpointWithId(String endpointKey, String id) {
        return getEndpoint(endpointKey) + "/" + id;
    }
}


