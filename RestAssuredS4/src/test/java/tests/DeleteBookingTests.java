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


@Epic("Movie Booking API")
@Feature("Delete Booking Functionality")
public class DeleteBookingTests extends BaseTest {

    @Test(dataProvider = "deleteBookingData", dataProviderClass = DataProviderUtils.class, groups = {"deleteBooking"})
    @Story("Delete Booking Operations")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates delete booking API across positive and negative test cases")
    public void testDeleteBooking(Map<String, Object> testData) {

        int expectedStatus = Integer.parseInt(testData.get("expectedStatusCode").toString());
        String endpointKey = testData.get("endpoint").toString();
        String acceptHeader = testData.getOrDefault("accept", "application/json").toString();

 System.out.println("🔗 Endpoint: " + getEndpointWithId(endpointKey, testData.get("bookingId").toString()));
        // Prepare request with headers
        var request = given()
                .log().all()  // 🚀 LOGS ALL REQUEST DETAILS
                .header("Accept", acceptHeader)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + getAuthToken());

        // Resolve endpoint URL
        String finalEndpoint = endpointKey.contains("by_id") && testData.containsKey("bookingId")
                ? getEndpointWithId(endpointKey, testData.get("bookingId").toString())
                : getEndpoint(endpointKey);
    
        System.out.println("🔗 Endpoint: " + finalEndpoint);
        // Send DELETE request with Hamcrest validations
        request
                .when()
                    .delete(finalEndpoint)
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
                    .body(expectedStatus == 200 ? containsString("bookingId") : anything());

        System.out.println("✅ Delete booking test completed with Hamcrest validations");
    }
}
