package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DataProviderUtils {

  
    
    @DataProvider(name = "authenticationData")
    public static Object[][] authenticationData() throws Exception {
        return loadAllTestData("authenticationTestData.json");
    }

    @DataProvider(name = "bookingData") 
    public static Object[][] bookingData() throws Exception {
        return loadAllTestData("bookingTestData.json");
    }

    @DataProvider(name = "viewBookingData")
    public static Object[][] viewBookingData() throws Exception {
        return loadAllTestData("viewBookingTestData.json");
    }

    @DataProvider(name = "deleteBookingData")
    public static Object[][] deleteBookingData() throws Exception {
        return loadAllTestData("deleteBookingTestData.json");
    }

    // 🔍 FILTERED DATA PROVIDERS - For specific test scenarios
    
    @DataProvider(name = "viewBookingListData")
    public static Object[][] viewBookingListData() throws Exception {
        return loadFilteredTestData("viewBookingTestData.json", "VIEW-");
    }

    @DataProvider(name = "viewBookingByIdData")
    public static Object[][] viewBookingByIdData() throws Exception {
        return loadFilteredTestData("viewBookingTestData.json", "VIEW_BY_ID-");
    }

    @DataProvider(name = "deleteBookingByIdData")
    public static Object[][] deleteBookingByIdData() throws Exception {
        return loadFilteredTestData("deleteBookingTestData.json", "DEL_BY_ID-");
    }

    
    private static Object[][] loadAllTestData(String fileName) throws Exception {
        
        String fullPath = "src/test/resources/testdata/" + fileName;
        
        
        List<Map<String, Object>> testDataList = readJsonFile(fullPath);
        
       
        return convertToTestNGFormat(testDataList);
    }

    
    private static Object[][] loadFilteredTestData(String fileName, String idPrefix) throws Exception {
        
        String fullPath = "src/test/resources/testdata/" + fileName;
        
       
        List<Map<String, Object>> allTestData = readJsonFile(fullPath);
        
       
        List<Map<String, Object>> filteredData = filterTestDataById(allTestData, idPrefix);
        
        
        return convertToTestNGFormat(filteredData);
    }

    
    private static List<Map<String, Object>> readJsonFile(String filePath) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        
       
        @SuppressWarnings("unchecked")
        Map<String, Object>[] dataArray = jsonMapper.readValue(new File(filePath), Map[].class);
        
       
        return Arrays.asList(dataArray);
    }

   
    private static List<Map<String, Object>> filterTestDataById(List<Map<String, Object>> allData, String idPrefix) {
        List<Map<String, Object>> filteredData = new ArrayList<>();
        
        for (Map<String, Object> testCase : allData) {
            String testId = testCase.get("id").toString();
            if (testId.startsWith(idPrefix)) {
                filteredData.add(testCase);
            }
        }
        
        return filteredData;
    }

   
    private static Object[][] convertToTestNGFormat(List<Map<String, Object>> testDataList) {
        Object[][] testNGFormat = new Object[testDataList.size()][1];
        
        for (int i = 0; i < testDataList.size(); i++) {
            testNGFormat[i][0] = testDataList.get(i);
        }
        
        return testNGFormat;
    }
}
