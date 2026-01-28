package testcases.authentication;

import base.BaseTest;
import config.ConfigManager;
import helpers.verifications.AuthVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import reports.ExtentReportManager;

public class TC19_PasswordCaseSensitiveTest extends BaseTest {

    @Test(description = "Test Login is blocked if password has wrong casing")
    public void testPasswordIsCaseSensitive() {

        SoftAssert softAssert =  new SoftAssert();

        ExtentReportManager.info("Navigate to Login page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginPage();

        ExtentReportManager.info("Attempt login with valid username and incorrect password");
        // Get login credentials of default test user
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        // Generate password with wrong casing and attempt login
        String passwordWrongCasing = password.toUpperCase();   // default mixed case
        loginPage.fillLoginFormThenSubmit(username, passwordWrongCasing);

        // Verify login failed: alert displayed + alert text + top bar user profile not shown
        ExtentReportManager.info("Verify unsuccessful login");
        AuthVerificationHelper.verifyInvalidCredentialsLoginError(loginPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}