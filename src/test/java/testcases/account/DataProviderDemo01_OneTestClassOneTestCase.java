package testcases.account;

import base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

public class DataProviderDemo01_OneTestClassOneTestCase extends BaseTest {

    @Test(dataProvider = "getTestData")
    public void DataProviderDemo01_TestCaseDemo(Map<String, String> data) {
        LOG.info("Thread ID: " + Thread.currentThread().getId() + " - Data: " + data.get("No."));
//        System.out.println(data.get("No."));
//        System.out.println(data.get("CountryCode"));
//        System.out.println(data.get("Address1"));
    }
}
