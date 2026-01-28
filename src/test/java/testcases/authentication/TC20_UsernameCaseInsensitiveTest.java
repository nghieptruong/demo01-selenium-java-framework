package testcases.authentication;

import base.BaseTest;
import config.ConfigManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.verifications.AuthVerificationHelper.verifyLoginSuccess;

public class TC20_UsernameCaseInsensitiveTest extends BaseTest {

    @Test(description = "Test User can Login with Username in different casing")
    public void testUsernameIsCaseInsensitive() {
        SoftAssert softAssert =  new SoftAssert();

        ExtentReportManager.info("Navigate to Login page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginPage();

        ExtentReportManager.info("Fill valid credentials and submit login form");
        // Get login credentials of default test user
        String username = ConfigManager.getDefaultUserUsername();  // default lower case
        String password = ConfigManager.getDefaultUserPassword();

        // Generate username in different casing and login
        String usernameDifferentCasing = username.toUpperCase();
        loginPage.fillLoginFormThenSubmit(usernameDifferentCasing, password);

        // Verify login success: alert displayed + message text + top bar user profile
        ExtentReportManager.info("Verify successful login");
        verifyLoginSuccess(loginPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}
