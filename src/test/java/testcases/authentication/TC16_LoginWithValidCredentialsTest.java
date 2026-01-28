package testcases.authentication;

import base.BaseTest;
import config.ConfigManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.verifications.AuthVerificationHelper.verifyLoginSuccess;

public class TC16_LoginWithValidCredentialsTest extends BaseTest {

    @Test(description = "Test succesful login with valid credentials")
    public void testSuccessfulLoginWithValidCredentials() {
        SoftAssert softAssert =  new SoftAssert();

        ExtentReportManager.info("Navigate to Login page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginPage();

        ExtentReportManager.info("Fill valid credentials and submit login form");
        // Get login credentials of default test user
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        //Login
        loginPage.fillLoginFormThenSubmit(username, password);

        // Verify login success: alert displayed + message text + top bar user profile
        ExtentReportManager.info("Verify successful login");
        verifyLoginSuccess(loginPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}
