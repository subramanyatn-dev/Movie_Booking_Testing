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
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;
import utils.DataProviderUtils;

@Epic("Booking API Tests")
@Feature("Add Booking Feature")
public class BookingTests extends BaseTest {

    @Test(dataProvider = "bookingData", dataProviderClass = DataProviderUtils.class, groups = {"booking"})
    @Description("Verify booking API with multiple positive and negative scenarios")
    @Story("Booking Creation")
    public void createBookingTest(Map<String, Object> testData) {

        String endpoint = getEndpoint(testData.get("endpoint").toString());
        
        int expectedStatus = Integer.parseInt(testData.get("expectedStatusCode").toString());

        var request = given()
                .log().all()
                .header("Authorization", "Bearer " + getAuthToken())
                .contentType("application/x-www-form-urlencoded")
                .accept("application/json");
        // Add params only if present
        if (testData.containsKey("bookingId")) {
            request.formParam("bookingId", testData.get("bookingId"));
        }
        if (testData.containsKey("userId")) {
            request.formParam("userId", testData.get("userId"));
        }
        if (testData.containsKey("movieTitle")) {
            request.formParam("movieTitle", testData.get("movieTitle"));
        }
        if (testData.containsKey("bookingDate")) {
            request.formParam("bookingDate", testData.get("bookingDate"));
        }
        if (testData.containsKey("ticketCount")) {
            request.formParam("ticketCount", testData.get("ticketCount"));
        }

        
        var response = request
                .when()
                    .post(endpoint)
                .then()
                    .log().all() 
                    .statusCode(expectedStatus)
                    .and()
                    .time(lessThan(1500L))  
                    .and()
                    .header("Content-Type", notNullValue()) 
                    .and()
                    .header("Content-Length", greaterThan("0"))  
                    .and()
                    .header("Date", matchesPattern(".*\\d{4}.*")) 
                    .and()
                    .body(expectedStatus == 200 ? containsString("bookingId") : anything())
                    .and()
                    .extract();
        

        System.out.println("-=-=-=-=-=-= Test completed with essential Hamcrest validations-=-=-=-=-=-=-==-=-=");
    }
} 
