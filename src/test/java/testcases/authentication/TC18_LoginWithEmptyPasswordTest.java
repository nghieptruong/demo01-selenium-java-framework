package testcases.authentication;

import base.BaseTest;
import config.ConfigManager;
import helpers.verifications.AuthVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import reports.ExtentReportManager;

public class TC18_LoginWithEmptyPasswordTest extends BaseTest {

    @Test(description = "Test Blocked Login with empty password field")
    public void testEmptyFieldValidation() {
        SoftAssert softAssert =  new SoftAssert();
        ExtentReportManager.info("Navigate to Login page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginPage();

        ExtentReportManager.info("Attempt login with empty password field" );
        // Get username of default test user
        String username = ConfigManager.getDefaultUserUsername();

        // Attempt login with empty password
        loginPage.fillLoginFormThenSubmit(username, "");

        // Verify empty field validation error message & user not logged in
        ExtentReportManager.info("Verify unsuccessful login due to empty password field");
        AuthVerificationHelper.verifyEmptyPasswordLoginError(loginPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}