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
@Feature("View Booking API")
public class ViewBookingTests extends BaseTest {

    @Test(dataProvider = "viewBookingData", dataProviderClass = DataProviderUtils.class, groups = {"viewBooking"})
    @Story("View Booking Operations")
    @Severity(SeverityLevel.NORMAL)
    @Description("Runs view booking API test cases for different scenarios")
    public void testViewBooking(Map<String, Object> testData) {

        String endpointKey = testData.get("endpoint").toString();
        int expectedStatus = Integer.parseInt(testData.get("expectedStatusCode").toString());
        String acceptHeader = testData.getOrDefault("accept", "application/json").toString();

        // Build request
        var request = given()
                .log().all() 
                .header("Accept", acceptHeader);

        // Resolve endpoint (with ID if needed)
        String finalEndpoint = endpointKey.contains("by_id") && testData.containsKey("bookingId")
                ? getEndpointWithId(endpointKey, testData.get("bookingId").toString())
                : getEndpoint(endpointKey);

        // Send GET request with Hamcrest validations
        request
                .when()
                    .get(finalEndpoint)
                .then()
                    .log().all() 
                    .statusCode(expectedStatus)
                    .and()
                    .time(lessThan(3000L))  
                    .and()
                    .header("Content-Type", notNullValue())  
                    .and()
                    .header("Content-Length", greaterThan("0"))  
                    .and()
                    .header("Date", matchesPattern(".*\\d{4}.*")) 
                    .and()
                    .body(expectedStatus == 200 ? containsString("booking") : anything()); 

        System.out.println("✅ View booking test completed with Hamcrest validations");
    }
}
