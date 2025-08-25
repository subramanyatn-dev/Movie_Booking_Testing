package tests;

import java.util.Map;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;
import utils.DataProviderUtils;

@Epic("Movie Booking API Tests")
@Feature("Authentication API")
public class AuthenticationTests extends BaseTest {

    @Test(dataProvider = "authenticationData", dataProviderClass = DataProviderUtils.class, groups = {"auth"})
    @Description("Authentication API test cases covering login and token validation scenarios")
    @Story("User Authentication")
    @Severity(SeverityLevel.NORMAL)
    public void testAuthentication(Map<String, Object> testData) {

        String endpoint = getEndpoint(testData.get("endpoint").toString());
        int expectedStatus = Integer.parseInt(testData.get("expectedStatusCode").toString());

        var request = given()
                .log().all()  
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded");

        // Add request params based on test data
        if (testData.containsKey("username")) {
            request.formParam("username", testData.get("username"));
        }
        if (testData.containsKey("password")) {
            request.formParam("password", testData.get("password"));
        }
        if (testData.containsKey("auth_code")) {
            String code = testData.get("auth_code").equals("DYNAMIC_AUTH_CODE")
                    ? getAuthCode()
                    : testData.get("auth_code").toString();
            request.formParam("auth_code", code);
        }

        // Send request with Hamcrest validations
        request
                .when()
                    .post(endpoint)
                .then()
                    .log().all()  // 🚀 LOGS ALL RESPONSE DETAILS
                    .statusCode(expectedStatus)
                    .and()
                    .time(lessThan(3000L))  // 🚀 Response time < 3 seconds
                    .and()
                    .header("Content-Type", notNullValue())  // 🚀 Content-Type exists
                    .and()
                    .header("Content-Length", greaterThan("0"))  // 🚀 Has content
                    .and()
                    .header("Date", matchesPattern(".*\\d{4}.*"))  // 🚀 Date has 4-digit year
                    .and()
                    .body(expectedStatus == 200 ? containsString("auth_code") : anything());  // 🚀 Contains auth data if 200

        // Additional specific validations
        if (Boolean.TRUE.equals(testData.get("expectAuthCode"))) {
            // Already validated above with containsString("auth_code")
        }
        if (Boolean.TRUE.equals(testData.get("expectAccessToken"))) {
            // Additional token validation can be added here if needed
        }

        System.out.println("✅ Authentication test completed with Hamcrest validations");
    }
}
